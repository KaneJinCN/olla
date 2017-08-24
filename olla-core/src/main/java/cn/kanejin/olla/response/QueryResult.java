package cn.kanejin.olla.response;

import java.io.Serializable;
import java.util.List;

/**
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

	public void setPaging(int page, int pageSize, int totalCount) {
		this.paging = new Paging(page, pageSize, totalCount);
	}

	public static class Paging {
		private int page;
		private int pageCount;
		private int pageSize;
		private int totalCount;
		
		public Paging(int page, int pageSize, int totalCount) {
			this.pageSize = pageSize;
			this.totalCount = totalCount;
			this.pageCount = ((totalCount - 1) / pageSize) + 1;
			// NOTE: total page is the max page number.
			this.page = Math.min(page, this.pageCount);
		}

		/**
		 * @return 第几页
		 */
		public int getPage() {
			return page;
		}

		/**
		 * @return 总页数
		 */
		public int getPageCount() {
			return pageCount;
		}

		/**
		 * @return 每页条数
		 */
		public int getPageSize() {
			return pageSize;
		}

		/**
		 * @return 总条数
		 */
		public int getTotalCount() {
			return totalCount;
		}
	}
}
