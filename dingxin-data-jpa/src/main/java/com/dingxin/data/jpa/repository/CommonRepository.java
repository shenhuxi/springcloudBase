package com.dingxin.data.jpa.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import com.dingxin.data.jpa.search.current.QLFinder;



/**
 * 描述:
 * 作者: qinzhw
 * 创建时间:
 **/
@NoRepositoryBean
public interface CommonRepository<T,ID extends Serializable>  extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {


	/**
     * 根据查询参数查询
     * 
     * @param searchParams
     *            【过滤条件】
     * @param pageNumber
     *            【当前页数】
     * @param pageSize
     *            【每页行数】
     * @param orders
     *            【排序】
     * @return
     */
    public Page<T> findPageByParams(Map<String, Object> searchParams, int pageNumber, int pageSize, Order... orders);
   
    /**
     * 根据查询参数查询
     * @param searchParams 【过滤条件】
     * @param pageRequest 【分页参数对象】
     * @return
     */
    public Page<T> findPageByParams(Map<String, Object> searchParams, PageRequest pageRequest);
    
    /**
     * 按JQL分页查询
     * @param jpql
     * @param searchParams 【过滤条件】
     * @param pageRequest 【分页参数对象】
     * @return
     */
    public Page<T> findPageByJPQL(String jpql,Map<String, Object> searchParams, PageRequest pageRequest);
    
    /**
     * 原生SQL及查询条件查询数据
     * 
     * @param sql
     *            SQL脚本,不包含searchParams中的查询条件
     * @param searchParams
     *            查询条件
     * @param pageNumber
     *            第几页
     * @param pageSize
     *            每页大小
     * @param clz
     *            返回的类型
     * @param orders
     *            排序
     */
    @SuppressWarnings("rawtypes")
    public Page<T> findPageByNativeSQLAndParams(String sql, Map<String, Object> searchParams, int pageNumber, int pageSize, Class clz, Order... orders);

    /**
     * 根据原生SQL及查询条件查询数据
     * @param sql
     * @param searchParams 查询条件
     * @param clz 对象类名
     * @param pageRequest 分页参数对象
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Page<T> findPageByNativeSQLAndParams(String sql, Map<String, Object> searchParams, Class clz, PageRequest pageRequest) ;
    
    /**
     * 根据原生SQL查询数据
     * @param sql
     * @param searchParams 查询条件
     * @param clz 对象类名
     * @param pageRequest 分页参数对象
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Page<T> findPageByNativeSQL(String sql, Class clz, PageRequest pageRequest) ;
    
    /**
     * 根据查询条件查询
     * @param searchParams
     * @return
     */
    public List<T> findListByParams(Map<String, Object> searchParams);
    
    /**
     * 原生SQL和查询条件查询
     * @param sql
     * @param searchParams
     * @param clz
     * @param sort
     * @return
     */
    @SuppressWarnings("rawtypes")
    public List findListByNativeSQLAndParams(String sql, Map<String, Object> searchParams, Class clz, Sort sort);
    @SuppressWarnings("rawtypes")
    
    public Page<T> findPageByQLFinder(QLFinder qlFinder, int pageNumber, int pageSize, Class clz, Order... orders) ;
}
