package cn.kanejin.olla;

import java.io.Serializable;

/**
 * 接口Null不做任何事情
 * <p>
 * 它只是在服务接口返回值的定义中表示<em>无需返回任何具体的对象</em>。
 * <p>
 * 例如：
 * <pre>
 * Result&lt;Null&gt; removeMember(Long entId, Long userId);
 * 表示调用删除成员的服务后，返回值{@link Result Result}的data里不带有任何具体的对象。
 * </pre>
 * 
 * @version $Id: Null.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 * @see Service
 * @see Result
 */
public interface Null extends Serializable {}
