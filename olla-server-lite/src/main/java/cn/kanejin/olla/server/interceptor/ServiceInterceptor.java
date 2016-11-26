package cn.kanejin.olla.server.interceptor;

import cn.kanejin.olla.ServiceContext;
import cn.kanejin.olla.ServiceContextHolder;
import cn.kanejin.olla.annotation.Cached;
import cn.kanejin.olla.annotation.ServiceGuard;
import cn.kanejin.olla.response.FailureResult;
import cn.kanejin.olla.response.ServiceResult;
import cn.kanejin.olla.response.ServiceResultStatus;
import cn.kanejin.olla.server.util.MD5Utils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Kane Jin
 * @version $Id: ServiceInterceptor.java 165 2016-07-31 03:17:02Z Kane $
 */
@Component("serviceInterceptor")
@Aspect
public class ServiceInterceptor {
    private static Logger log = LoggerFactory.getLogger(ServiceInterceptor.class);

    @Autowired
    private ApplicationContext appContext;

    @Autowired(required = false)
    @Qualifier("serviceCacheStorage")
    private ServiceCacheStorage serviceCacheStorage;

    private static String convertArgToString(Object arg) {
        if (arg == null)
            return null;
        if (!arg.getClass().isArray())
            return arg.toString();

        StringBuilder result = new StringBuilder("[");
        Object[] subArgs = (Object[]) arg;
        for (int j = 0; j < subArgs.length; j++) {
            if (j != 0) result.append(",");
            result.append(convertArgToString(subArgs[j]));
        }
        return result.append("]").toString();
    }

    /**
     * 对服务做一层代理，处理缓存、监控执行性能并处理异常情况
     *
     * @param pjp
     * @return
     */
    @Around("execution(* *..*ServiceImpl.*(..))")
    public Object aroundService(ProceedingJoinPoint pjp) throws Throwable {

        boolean isCachedService = isCachedMethod(pjp);
        String cacheKey = null;
        ServiceResult<?> retVal = null;

        // 从缓存中查找
        if (serviceCacheStorage != null && isCachedService) {
            cacheKey = getCacheKey(pjp);

            ServiceContext context = ServiceContextHolder.get();
            boolean useCache = (context == null || context.isUseCache());
            if (useCache) {
                retVal = serviceCacheStorage.getResult(cacheKey);

                if (retVal != null) {
                    return retVal;
                }
            }
        }

        ServiceGuard sg = getServiceGuard(pjp);
        if (sg != null) {
            retVal = proceedGuard(sg, pjp);
            if (retVal == null || retVal.isOk()) {
                retVal = proceedServiceMethod(pjp);
            }
        } else {
            retVal = proceedServiceMethod(pjp);
        }

        // 把正常的结果放入缓存中
        if (serviceCacheStorage != null
                && isCachedService
                && cacheKey != null && !cacheKey.isEmpty()
                && retVal != null && retVal.isOk()) {
            serviceCacheStorage.putResult(cacheKey, retVal);
        }

        return retVal;
    }

    private ServiceResult<?> proceedGuard(ServiceGuard sg, ProceedingJoinPoint pjp) {
        String[] guardBeanNames = sg.value();

        if (guardBeanNames == null || guardBeanNames.length == 0)
            return null;

        for (String gbn : guardBeanNames) {
            ServiceResult<?> r = proceedOneGuard(gbn, pjp);
            if (r != null && !r.isOk())
                return r;
        }

        return null;
    }

    @SuppressWarnings("rawtypes")
    private ServiceResult<?> proceedOneGuard(String guardBeanName, ProceedingJoinPoint pjp) {
        Object gb = appContext.getBean(guardBeanName);

        if (gb == null)
            return null;

        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Method guardMethod = ClassUtils.getMostSpecificMethod(method, gb.getClass());
        guardMethod = BridgeMethodResolver.findBridgedMethod(guardMethod);

        if (guardMethod == null)
            return null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("Proceeding Service Guard [{}]", guardMethod.getName());
            }

            return (ServiceResult<?>) guardMethod.invoke(gb, pjp.getArgs());
        } catch (Throwable t) {
            log.error("Service Error", t);
            return new FailureResult(ServiceResultStatus.ERROR, "Service Error");
        }
    }

    @SuppressWarnings({"rawtypes", "deprecation"})
    private ServiceResult<?> proceedServiceMethod(ProceedingJoinPoint pjp) {
        ServiceResult<?> retVal = null;
        try {
            if (log.isDebugEnabled()) {
                log.debug("Proceeding Service Method [{}]", pjp.getSignature());
            }

            retVal = (ServiceResult<?>) pjp.proceed();
        } catch (Throwable t) {
            log.error("Service Error", t);
            retVal = new FailureResult(ServiceResultStatus.ERROR, "Service Error");
        }
        return retVal;
    }

    @After("execution(* *..*ServiceImpl.*(..))")
    public void cleanContext() throws Throwable {
        // 清除LocalThread里的service context
        log.debug("Cleaning Service Context");
        ServiceContextHolder.remove();
    }

    private String getCacheKey(ProceedingJoinPoint pjp) {
        StringBuilder key = new StringBuilder(pjp.getSignature().toString());
        Object[] args = pjp.getArgs();

        // 把参数串成一个字符串
        key.append("{");
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (i != 0) key.append(",");
                key.append(convertArgToString(arg));
            }
        }
        key.append("}");

        // 如果key太长，则使用MD5编码
        String result = "_sc_" + (key.length() > 200 ? MD5Utils.md5(key.toString()) : key.toString());

        log.debug("Generate Cache key [{}] for [{}]", result, key);
        return result;
    }

    private ServiceGuard getServiceGuard(ProceedingJoinPoint pjp) {
        Class<?> targetClass = (pjp.getThis() != null ? AopUtils.getTargetClass(pjp.getThis()) : null);
        if (targetClass == null)
            return null;

        // 服务是否有ServiceValidated注解
        ServiceGuard guard = targetClass.getAnnotation(ServiceGuard.class);
        if (guard == null) {
            for (Annotation metaAnn : targetClass.getAnnotations()) {
                guard = metaAnn.annotationType().getAnnotation(ServiceGuard.class);
                if (guard != null) {
                    break;
                }
            }
        }

        return guard;
    }

    private boolean isCachedMethod(ProceedingJoinPoint pjp) {
        Class<?> targetClass = (pjp.getThis() != null ? AopUtils.getTargetClass(pjp.getThis()) : null);
        if (targetClass == null)
            return false;

        // 接口的方法是否有Cached注解
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        Cached ann = method.getAnnotation(Cached.class);
        if (ann == null) {
            for (Annotation metaAnn : method.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(Cached.class);
                if (ann != null) {
                    break;
                }
            }
        }
        if (ann != null)
            return true;

        // 实现接口的方法是否有Cached注解
        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        ann = specificMethod.getAnnotation(Cached.class);
        if (ann == null) {
            for (Annotation metaAnn : specificMethod.getAnnotations()) {
                ann = metaAnn.annotationType().getAnnotation(Cached.class);
                if (ann != null) {
                    break;
                }
            }
        }
        return ann != null;
    }
}
