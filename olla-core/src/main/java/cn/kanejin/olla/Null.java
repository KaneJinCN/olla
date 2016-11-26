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
 * 表示调用删除成员的服务后，返回值{@link ServiceResult ServiceResult}的data里不带有任何具体的对象。
 * </pre>
 * 
 * @author Kane Jin
 * @see Service
 * @see ServiceResult
 */
public interface Null extends Serializable {}
