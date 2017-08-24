package cn.kanejin.olla.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询信息（包含查询条件参数信息和排序信息）
 * <p>
 * <em>如果排序信息不指定的话，则按默认的排序进行查询。</em>
 * 
 * @author Kane Jin
 */
public abstract class QueryRequest implements Serializable {
	private static final long serialVersionUID = 3684987356242401711L;

	private Map<String, Object> params;
	private List<OrderBy> orderBy;
	
	public QueryRequest() {
		this.params = new HashMap<String, Object>();
	}
	
	/**
	 * @param params 查询条件参数
	 */
	public QueryRequest(Map<String, Object> params) {
		if (params == null)
			throw new IllegalArgumentException("Params must not be null");
		this.params = params;
	}
	
	/**
	 * 获取查询条件参数
	 * <p>
	 * 此参数里不包含排序和分页的参数
	 * 
	 * 
	 * @return 查询条件参数
	 */
	public Map<String, Object> getParameters() {
		return params;
	}
	
	/**
	 * 往查询条件里添加参数
	 * 
	 * @param key 参数键
	 * @param value 参数值
	 */
	protected void setParameter(String key, Object value) {
		params.put(key, value);
	}
	
	/**
	 * 获取排序的字符串
	 * <p>
	 * 返回的字符串是SQL能直接使用的order by语句，比如：
	 * <pre>
	 * name asc, age desc
	 * </pre>
	 * 
	 * @return 排序的字符串
	 */
	public String getOrderBy() {
		if (orderBy == null || orderBy.isEmpty())
			return null;

		StringBuilder result = new StringBuilder();
		for (OrderBy o : orderBy) {
			if (result.length() != 0)
				result.append(", ");
			result.append(o.toString());
		}
		
		return result.toString();
	}

	/**
	 * 添加一个排序字段（升序）
	 * 
	 * @param name 排序字段名
	 */
	protected void addOrderBy(String name) {
		this.addOrderBy(name, true);
	}
	
	/**
	 * 添加一个排序字段（需指定升降序）
	 * 
	 * @param name 排序字段名
	 * @param isAsc 是否升序
	 */
	protected void addOrderBy(String name, boolean isAsc) {
		if (orderBy == null)
			orderBy = new ArrayList<OrderBy>();

		orderBy.add(new OrderBy(name, isAsc));
	}
	
	@Override
	public String toString() {
		return "{params=" + params + ", orderBy=" + getOrderBy() + "}";
	}

	private static class OrderBy {
		private String name;
		private boolean isAsc;
		
		public OrderBy(String name, boolean isAsc) {
			this.name = name;
			this.isAsc = isAsc;
		}

		@Override
		public String toString() {
			return name + (isAsc ? " asc" : " desc");
		}
	}
}
