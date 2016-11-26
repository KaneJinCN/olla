package cn.kanejin.olla;

/**
 * @author Kane Jin
 */
public abstract class AbstractService implements Service {

	@Override
	public void setServiceContext(ServiceContext sc) {
		ServiceContextHolder.set(sc);
	}

	protected ServiceContext getServiceContext() {
		return ServiceContextHolder.get();
	}
}
