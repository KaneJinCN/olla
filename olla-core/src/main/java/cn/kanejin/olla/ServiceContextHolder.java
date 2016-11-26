package cn.kanejin.olla;


/**
 * @version $Id: ServiceContextHolder.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class ServiceContextHolder {
	private static final ThreadLocal<ServiceContext> holder = new ThreadLocal<ServiceContext>();
	
	public static void set(ServiceContext serviceContext) {
		holder.set(serviceContext);
	}
	
	public static ServiceContext get() {
		return holder.get();
	}
	
	public static void remove() {
		holder.remove();
	}
}
