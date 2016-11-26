package cn.kanejin.olla.request;

/**
 * @author Kane Jin
 */
public enum RequesterLevel {
	/** 
	 * 匿名者
	 */
	ANONYMOUS,
	/** 
	 * 用户
	 * <p>
	 * 包括一切的网站用户：注册用户、激活用户、实名用户、企业用户、企业管理员 
	 */
	USER,
	/** 
	 * 管理员
	 * <p>
	 * 从后台登录的网站管理员
	 */
	ADMIN,
	/**
	 * 系统
	 */
	SYSTEM;

	public boolean isOrHigherThanAnonymous() {
		return this == ANONYMOUS || this == USER || this == ADMIN || this == SYSTEM;
	}
	public boolean isOrHigherThanUser() {
		return this == USER || this == ADMIN || this == SYSTEM;
	}
	public boolean isOrHigherThanAdmin() {
		return this == ADMIN || this == SYSTEM;
	}
	public boolean isOrHigherThanSystem() {
		return this == SYSTEM;
	}
}
