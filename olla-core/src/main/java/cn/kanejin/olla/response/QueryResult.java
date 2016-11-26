package cn.kanejin.olla.response;

import java.io.Serializable;
import java.util.List;

/**
 * @version $Id: QueryResult.java 52 2015-04-04 06:45:23Z Kane $
 * @author Kane Jin
 */
public class QueryResult<T> implements Serializable {
	private static final long serialVersionUID = 976212694198208191L;
	
	private Paging paging;
	
	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public Paging getPaging() {
		return paging;
	}

	public void setPaging(int page, int limit, int total) {
		this.paging = new Paging(page, limit, total);
	}

	public static class Paging {
		private int page;
		private int totalPage;
		private int limit;
		private int total;
		
		public Paging(int page, int limit, int total) {
			this.limit = limit;
			this.total = total;
			this.totalPage = ((total - 1) / limit) + 1;
			// NOTE: total page is the max page number.
			this.page = Math.min(page, this.totalPage);
		}
		
		public int getPage() {
			return page;
		}

		public int getTotalPage() {
			return totalPage;
		}

		public int getLimit() {
			return limit;
		}

		public int getTotal() {
			return total;
		}
	}
}
