package cn.kanejin.olla;

/**
 * @version $Id: AbstractService.java 52 2015-04-04 06:45:23Z Kane $
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
