package cn.kanejin.olla.request;

/**
 * @version $Id: Requester.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public interface Requester {
	Object getId();

	String getName();
	
	String getIp();
	
	RequesterLevel getLevel();
}
