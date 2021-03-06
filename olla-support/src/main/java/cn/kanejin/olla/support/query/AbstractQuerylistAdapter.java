package cn.kanejin.olla.support.query;

import cn.kanejin.olla.request.QueryRequest;
import cn.kanejin.olla.response.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Kane Jin
 */
public abstract class AbstractQuerylistAdapter implements QuerylistAdapter {
    private static Logger log = LoggerFactory.getLogger(AbstractQuerylistAdapter.class);

    private String sqlmap;

    private int defaultNumPerPage = 10;

    private int maxNumPerPage = 100;

    private String defaultSort;

    @Override
    public <E> QueryResult<E> queryList(QueryRequest info, Integer page,
                                        Integer limit) {
        QueryResult<E> result = new QueryResult<E>();

        int total = count(info);

        if (page == null || page <= 0)
            page = 1;
        if (limit == null || limit <= 0)
            limit = getDefaultNumPerPage();
        if (limit > getMaxNumPerPage())
            limit = getMaxNumPerPage();

        // 如果page超过最大页数，则设置成最大页数
        if (page > 1 && ((page - 1) * limit > total - 1)) {
            page = ((total - 1) / limit) + 1;
        }

        result.setPaging(page, limit, total);

        if (total == 0) {
            result.setList(new ArrayList<E>());
            return result;
        }

        List<E> list = query(info, page, limit);

        result.setList(list);

        return result;
    }

    protected abstract <E> List<E> selectList(String sqlName, Map<String, Object> params);

    protected abstract Integer selectCount(String countSqlName, Map<String, Object> params);

    @Override
    public int count(QueryRequest info) {
        log.debug("Start to count total of list[{}]", getCountSqlmap());
        return selectCount(getCountSqlmap(), info.getParameters());
    }

    @Override
    public <E> List<E> queryTop(QueryRequest info, Integer top) {
        if (top == null || top <= 0)
            top = getDefaultNumPerPage();
        if (top > getMaxNumPerPage())
            top = getMaxNumPerPage();

        return query(info, 1, top);
    }

    private <E> List<E> query(QueryRequest info, Integer page, Integer limit) {
        log.debug("Start to query top list[{}]", getSqlmap());

        String orderBy = info.getOrderBy();
        if (orderBy == null || orderBy.isEmpty()) {
            orderBy = getDefaultSort();
            log.debug("The default sort '{}' was  set.", orderBy);
        }

        return selectList(
                getSqlmap(),
                mixOrderAndPagingParams(info.getParameters(), orderBy, page, limit));
    }

    private Map<String, Object> mixOrderAndPagingParams(
            Map<String, Object> params, String orderBy, int page, int limit) {

        Map<String, Object> map = new HashMap<String, Object>();

        if (params != null)
            map.putAll(params);

        map.put("order_by", orderBy);

        map.put("start_num", (page - 1) * limit);
        map.put("limit_num", limit);
        map.put("end_num", page * limit);

        return map;
    }

    public String getSqlmap() {
        return sqlmap;
    }

    public void setSqlmap(String sqlmap) {
        this.sqlmap = sqlmap;
    }

    public String getCountSqlmap() {
        return getSqlmap() + "Count";
    }

    public int getDefaultNumPerPage() {
        return defaultNumPerPage;
    }

    public void setDefaultNumPerPage(int defaultNumPerPage) {
        this.defaultNumPerPage = defaultNumPerPage;
    }

    public int getMaxNumPerPage() {
        return maxNumPerPage;
    }

    public void setMaxNumPerPage(int maxNumPerPage) {
        this.maxNumPerPage = maxNumPerPage;
    }

    public String getDefaultSort() {
        return defaultSort;
    }

    public void setDefaultSort(String defaultSort) {
        this.defaultSort = defaultSort;
    }
}
