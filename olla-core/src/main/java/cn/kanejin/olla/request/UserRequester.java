package cn.kanejin.olla.request;

/**
 * @version $Id: UserRequester.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class UserRequester implements Requester {
	
	private Long id;
	
	private String name;
	
	private String ip;

	public UserRequester(Long userId, String userName, String ip) {
		this.id = userId;
		this.name = userName;
		this.ip = ip;
	}
		
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getIp() {
		return ip;
	}

	@Override
	public RequesterLevel getLevel() {
		return RequesterLevel.USER;
	}
	
	@Override
	public String toString() {
		return "{id:" + getId() + ", level:" + getLevel() + "}";
	}
}
