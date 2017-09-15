package cn.kanejin.olla.request;

import java.io.Serializable;
import java.util.*;

/**
 * 查询信息（包含查询条件参数信息和排序信息）
 * <p>
 * <em>如果排序信息不指定的话，则按默认的排序进行查询。</em>
 * 
 * @author Kane Jin
 */
public abstract class QueryRequest implements Serializable {

	public enum Direction {
		ASC,
		DESC
	}

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
	 * @param column 排序字段
	 */
	protected void addOrderBy(String column) {
		this.addOrderBy(column, Direction.ASC);
	}
	
	/**
	 * 添加一个排序字段（需指定升降序）
	 *
	 *
	 * @deprecated 请使用addOrderBy(String, Direction)
	 * @param column 排序字段
	 * @param isAsc 是否升序
	 */
	@Deprecated
	protected void addOrderBy(String column, boolean isAsc) {
		addOrderBy(column, isAsc ? Direction.ASC : Direction.DESC);
	}

	/**
	 * 添加一个排序字段（需指定升降序）
	 *
	 *
	 * @param column 排序字段
	 * @param direction 排序方向
	 */
	protected void addOrderBy(String column, Direction direction) {
		if (orderBy == null)
			orderBy = new ArrayList<OrderBy>();

		orderBy.add(new OrderBy(column, direction));
	}

	@Override
	public String toString() {
		return "{params=" + params + ", orderBy=" + getOrderBy() + "}";
	}

	private static class OrderBy {
		private String column;
		private Direction direction;
		
		public OrderBy(String column, Direction direction) {
			this.column = column;
			this.direction = direction;
		}

		@Override
		public String toString() {
			return column + " " + direction;
		}
	}
}
