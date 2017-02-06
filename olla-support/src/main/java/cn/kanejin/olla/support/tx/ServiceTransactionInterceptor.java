package cn.kanejin.olla.support.tx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;

import cn.kanejin.olla.response.ServiceResult;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;
import org.springframework.transaction.support.CallbackPreferringPlatformTransactionManager;



/**
 * 当服务返回异常（ok=false）时，事务回滚
 *
 * @author Kane Jin
 */
public class ServiceTransactionInterceptor extends TransactionInterceptor {
    private static final long serialVersionUID = 124054498346164726L;


    /**
     * Create a new TransactionInterceptor.
     * <p>Transaction manager and transaction attributes still need to be set.
     * @see #setTransactionManager
     * @see #setTransactionAttributes(java.util.Properties)
     * @see #setTransactionAttributeSource(TransactionAttributeSource)
     */
    public ServiceTransactionInterceptor() {
        super();
    }

    /**
     * Create a new TransactionInterceptor.
     * @param ptm the transaction manager to perform the actual transaction management
     * @param attributes the transaction attributes in properties format
     * @see #setTransactionManager
     * @see #setTransactionAttributes(java.util.Properties)
     */
    public ServiceTransactionInterceptor(PlatformTransactionManager ptm, Properties attributes) {
        super(ptm, attributes);
    }

    /**
     * Create a new TransactionInterceptor.
     * @param ptm the transaction manager to perform the actual transaction management
     * @param tas the attribute source to be used to find transaction attributes
     * @see #setTransactionManager
     * @see #setTransactionAttributeSource(TransactionAttributeSource)
     */
    public ServiceTransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource tas) {
        super(ptm, tas);
    }

    public Object invoke(final MethodInvocation invocation) throws Throwable {
        // Work out the target class: may be <code>null</code>.
        // The TransactionAttributeSource should be passed the target class
        // as well as the method, which may be from an interface.
        Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);

        // If the transaction attribute is null, the method is non-transactional.
        final TransactionAttribute txAttr =
                getTransactionAttributeSource().getTransactionAttribute(invocation.getMethod(), targetClass);
        final PlatformTransactionManager tm = determineTransactionManager(txAttr);
        final String joinpointIdentification = methodIdentification(invocation.getMethod(), targetClass);

        if (txAttr == null || !(tm instanceof CallbackPreferringPlatformTransactionManager)) {
            // Standard transaction demarcation with getTransaction and commit/rollback calls.
            TransactionInfo txInfo = createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
            Object retVal = null;
            try {
                // This is an around advice: Invoke the next interceptor in the chain.
                // This will normally result in a target object being invoked.
                retVal = invocation.proceed();

                completeTransactionAfterReturning(txInfo, (ServiceResult<?>)retVal);
            }
            catch (Throwable ex) {
                // target invocation exception
                completeTransactionAfterThrowing(txInfo, ex);
                throw ex;
            }
            finally {
                cleanupTransactionInfo(txInfo);
            }

            return retVal;
        } else
            return super.invoke(invocation);
    }

    protected void completeTransactionAfterReturning(TransactionInfo txInfo, ServiceResult<?> result) {
        if (txInfo != null && txInfo.hasTransaction()) {
            if (logger.isDebugEnabled()) {
                logger.debug("Completing transaction for [" + txInfo.getJoinpointIdentification() + "]");
            }
            if (result == null || !result.isOk()) {
                txInfo.getTransactionManager().rollback(txInfo.getTransactionStatus());
            } else {
                txInfo.getTransactionManager().commit(txInfo.getTransactionStatus());
            }
        }
    }

    //---------------------------------------------------------------------
    // Serialization support
    //---------------------------------------------------------------------

    private void writeObject(ObjectOutputStream oos) throws IOException {
        // Rely on default serialization, although this class itself doesn't carry state anyway...
        oos.defaultWriteObject();

        // Deserialize superclass fields.
        oos.writeObject(getTransactionManagerBeanName());
        oos.writeObject(getTransactionManager());
        oos.writeObject(getTransactionAttributeSource());
        oos.writeObject(getBeanFactory());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        // Rely on default serialization, although this class itself doesn't carry state anyway...
        ois.defaultReadObject();

        // Serialize all relevant superclass fields.
        // Superclass can't implement Serializable because it also serves as base class
        // for AspectJ aspects (which are not allowed to implement Serializable)!
        setTransactionManagerBeanName((String) ois.readObject());
        setTransactionManager((PlatformTransactionManager) ois.readObject());
        setTransactionAttributeSource((TransactionAttributeSource) ois.readObject());
        setBeanFactory((BeanFactory) ois.readObject());
    }
}
