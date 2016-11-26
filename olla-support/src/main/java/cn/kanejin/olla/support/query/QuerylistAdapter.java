package cn.kanejin.olla.support.query;

import cn.kanejin.olla.request.QueryRequest;
import cn.kanejin.olla.response.QueryResult;

import java.util.List;


/**
 * @version $Id: QuerylistAdapter.java 93 2015-08-18 07:06:36Z Kane $
 * @author Kane Jin
 */
public interface QuerylistAdapter {
	<E> QueryResult<E> queryList(QueryRequest request, Integer page, Integer limit);
	
	int count(QueryRequest request);
	
	<E> List<E> queryTop(QueryRequest request, Integer top);
}
