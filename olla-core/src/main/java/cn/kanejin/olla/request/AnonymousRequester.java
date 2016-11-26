package cn.kanejin.olla.request;

/**
 * @author Kane Jin
 */
public class AnonymousRequester implements Requester {
	
	public static final AnonymousRequester DEFAULT_REQUESTER = new AnonymousRequester("0.0.0.0");
	
	private String ip;

	public AnonymousRequester(String ip) {
		this.ip = ip;
	}
		
	@Override
	public Long getId() {
		return -1L;
	}

	@Override
	public String getName() {
		return "";
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public RequesterLevel getLevel() {
		return RequesterLevel.ANONYMOUS;
	}
	
	@Override
	public String toString() {
		return "{id:" + getId() + ", level:" + getLevel() + "}";
	}
}
