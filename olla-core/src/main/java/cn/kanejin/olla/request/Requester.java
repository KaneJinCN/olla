package cn.kanejin.olla.request;

/**
 * @author Kane Jin
 */
public interface Requester {
	Object getId();

	String getName();
	
	String getIp();
	
	RequesterLevel getLevel();
}
