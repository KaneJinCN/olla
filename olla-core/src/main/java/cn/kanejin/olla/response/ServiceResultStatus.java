package cn.kanejin.olla.response;

/**
 * @author Kane Jin
 */
public enum ServiceResultStatus {
	/**
	 * 服务调用成功
	 */
	OK						(200),
	
	/** 
	 * 调用服务的参数不符合要求
	 */
	BAD_PARAMETER			(400),
	/** 
	 * 不符合业务处理的要求
	 */
	NOT_MEET_BIZ			(401),
	/** 
	 * 没有调用服务的权限
	 */
	FORBIDDEN				(403),
	/**
	 * 服务未找到
	 */
	NOT_FOUND				(404),
	/**
	 * 服务超时
	 */
	TIMEOUT					(408),
	/**
	 * 服务内部异常
	 */
	ERROR					(500),
	/**
	 * 服务传递、分发过程出现异常
	 */
	BAD_ROUTE				(502),

	/**
	 * 服务不可用
	 */
	UNAVAILABLE				(503);


	private int code;
	
	private ServiceResultStatus(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return this.code;
	}
	
	@Override
	public String toString() {
		return String.valueOf(code);
	}
}
