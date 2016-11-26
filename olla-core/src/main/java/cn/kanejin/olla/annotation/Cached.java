package cn.kanejin.olla.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标明使用缓存的服务方法
 * 
 * @version $Id: Cached.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cached {
}
