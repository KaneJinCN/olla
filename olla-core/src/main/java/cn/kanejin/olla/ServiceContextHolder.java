package cn.kanejin.olla;


/**
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
