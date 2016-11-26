package cn.kanejin.olla.request;

/**
 * @version $Id: SystemRequester.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class SystemRequester implements Requester {
	private SystemRequester(){}
	
	private static SystemRequester requester;
	
	public static SystemRequester getInstance() {
		if (requester == null)
			requester = new SystemRequester();
		return requester;
	}
	
	@Override
	public Long getId() {
		return 0L;
	}

	@Override
	public String getName() {
		return "system";
	}

	@Override
	public RequesterLevel getLevel() {
		return RequesterLevel.SYSTEM;
	}

	@Override
	public String toString() {
		return "{id:" + getId() + ", level:" + getLevel() + "}";
	}

	@Override
	public String getIp() {
		return "0.0.0.0";
	}
}
