package cn.kanejin.olla.server;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @version $Id: ServiceApplicationContext.java 154 2016-04-20 15:25:06Z Kane $
 * @author Kane Jin
 */
public class ServiceApplicationContext {
	private static Logger log = LoggerFactory.getLogger(ServiceApplicationContext.class);

	private static Map<String, ApplicationContext> ACS = new ConcurrentHashMap<String, ApplicationContext>();
	
	private static ApplicationContext getAppContext(String namespace) {
		ApplicationContext ac = ACS.get(namespace);
		
		if (ac == null) {
			ac = loadServiceApplication(namespace);
		}
		
		return ac;
	}
	
	private static synchronized ApplicationContext loadServiceApplication(String namespace) {
		ApplicationContext ac = ACS.get(namespace);
		
		if (ac != null)
			return ac;
		
		try {
			String path = namespace.replace(".", "/");
			
			log.debug("Loading Service Configs for [{}]", namespace);
			
			ac = new ClassPathXmlApplicationContext(
					"classpath*:cn/kanejin/kplat/service/server/config/**/*.xml",
					"classpath*:" + path + "/config/**/*.xml");
			
			ACS.put(namespace, ac);
			
			return ac;
		} catch (Exception e) {
			log.error("Loading Service Configs Error", e);
		}
		
		return null;
	}
	
	public static Object getBean(String namespace, String beanName) {
		return getAppContext(namespace).getBean(beanName);
	}
	public static <T> T getBean(String namespace, String beanName, Class<T> clazz) {
		return getAppContext(namespace).getBean(beanName, clazz);
	}

	public static Object getService(String namespace, String serviceName) {
		return getBean(namespace, serviceName);
	}

	public static <T> T getService(String namespace, String serviceName, Class<T> clazz) {
		return getBean(namespace, serviceName, clazz);
	}
}
