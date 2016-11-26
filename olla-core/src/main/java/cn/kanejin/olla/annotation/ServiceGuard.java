package cn.kanejin.olla.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明使用校验的服务类
 * 
 * @version $Id: ServiceGuard.java 94 2015-08-18 07:10:02Z Kane $
 * @author Kane Jin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ServiceGuard {
	String[] value();
}
