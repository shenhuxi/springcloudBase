package com.dingxin.data.jpa.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.dingxin.data.jpa.former.MyResultTransformer;
import com.dingxin.data.jpa.search.current.DynamicSpecifications;
import com.dingxin.data.jpa.search.current.QLFinder;
import com.dingxin.data.jpa.search.current.SearchFilter;

/**
 * 自定义repository的方法接口实现类,该类主要提供自定义的公用方法
 * 
 * @author dicky
 *
 * @param <T>
 * @param <ID>
 */
public class MyCustomRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, Serializable>
		implements CommonRepository<T, Serializable> {

	private static final Logger LOG = LoggerFactory.getLogger(MyCustomRepository.class);

	private final EntityManager entityManager;

	public MyCustomRepository(Class<T> domainClass, EntityManager em) {
		super(domainClass, em);
		this.entityManager = em;
	}

	public MyCustomRepository(final JpaEntityInformation<T, ID> entityInformation, final EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;

	}

	/**
	 * 根据查询参数查询
	 * 
	 * @param searchParams
	 *            【过滤条件】
	 * @param pageNumber
	 *            【当前页数,从1开始】
	 * @param pageSize
	 *            【每页行数】
	 * @param orders
	 *            【排序】
	 * @return
	 */
	@Override
	public Page<T> findPageByParams(Map<String, Object> searchParams, int pageNumber, int pageSize, Order... orders) {
		return findPageByParams(searchParams, new PageRequest(pageNumber, pageSize, buildSort(orders)));
	}

	/**
	 * 根据查询参数查询
	 * 
	 * @param searchParams
	 *            【过滤条件】
	 * @param pageRequest
	 *            【分页参数对象】
	 * @return
	 */
	@Override
	public Page<T> findPageByParams(Map<String, Object> searchParams, PageRequest pageRequest) {
		return findAll(DynamicSpecifications.buildSpecification(searchParams, getDomainClass()), pageRequest);
	}

	/**
	 * 按JQL分页查询
	 * 
	 * @param jpql
	 * @param searchParams
	 *            【过滤条件】
	 * @param pageRequest
	 *            【分页参数对象】
	 * @return
	 */
	@Override
	public Page<T> findPageByJPQL(String jpql, Map<String, Object> searchParams, PageRequest pageRequest) {
		QLFinder qlFinder = new QLFinder(jpql, false);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, searchParams);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, this.getOrders(pageRequest.getSort()));
		return findPageByQLFinder(qlFinder, pageRequest, getDomainClass());
	}

	/**
	 * 根据原生SQL及查询条件查询数据
	 * 
	 * @param sql
	 *            一定要是原生SQL SQL脚本,不包含searchParams中的查询条件
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
	@Override
	public Page<T> findPageByNativeSQLAndParams(String sql, Map<String, Object> searchParams, int pageNumber,
			int pageSize, Class clz, Order... orders) {
		return findPageByNativeSQLAndParams(sql, searchParams, clz,
				new PageRequest(pageNumber, pageSize, buildSort(orders)));
	}

	/**
	 * 根据原生SQL及查询条件查询数据
	 * 
	 * @param sql
	 * @param searchParams
	 *            查询条件
	 * @param clz
	 *            对象类名
	 * @param pageable
	 *            分页参数对象
	 * @return
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public Page<T> findPageByNativeSQLAndParams(String sql, Map<String, Object> searchParams, Class clz,
			PageRequest pageRequest) {
		QLFinder qlFinder = new QLFinder(sql, true);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, searchParams);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, this.getOrders(pageRequest.getSort()));
		return findPageByQLFinder(qlFinder, pageRequest, clz);
	}

	/**
	 * 根据原生SQL查询数据
	 * 
	 * @param sql
	 * @param searchParams
	 *            查询条件
	 * @param clz
	 *            对象类名
	 * @param pageable
	 *            分页参数对象
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<T> findPageByNativeSQL(String sql, Class clz, PageRequest pageRequest) {
		return findPageByNativeSQLAndParams(sql, new HashMap<>(), clz, pageRequest);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Page<T> findPageByQLFinder(QLFinder qlFinder, int pageNumber, int pageSize, Class clz, Order... orders) {
		return findPageByQLFinder(qlFinder, new PageRequest(pageNumber, pageSize, buildSort(orders)), clz);
	}

	@Override
	public List<T> findListByParams(Map<String, Object> searchParams) {
		return findAll(DynamicSpecifications.buildSpecification(searchParams, getDomainClass()),
				new Sort(Sort.Direction.ASC, "id"));
	}

	/**
	 * 原生SQL和查询条件查询
	 * 
	 * @param sql
	 * @param searchParams
	 * @param clz
	 * @param sort
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List findListByNativeSQLAndParams(String sql, Map<String, Object> searchParams, Class clz, Sort sort) {
		QLFinder qlFinder = new QLFinder(sql, true);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, searchParams);
		qlFinder = SearchFilter.fillFilterToQLFinder(qlFinder, this.getOrders(sort));
		return findList(qlFinder, clz);
	}

	/**
	 * 查询分页方法
	 * @param qlFinder
	 * @param pageRequest
	 * @param clz
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Page<T> findPageByQLFinder(QLFinder qlFinder, PageRequest pageRequest, Class clz) {
		if (clz == null) {
			clz = getDomainClass();
		}
		LOG.info("数据对象类名，{}", clz.getName());
		
		long count = 0L;
		// 查询总记录数
		count = countQLResult(qlFinder, qlFinder.isNativeSQL());
		List<T> content = new ArrayList<>();
		Query query = null;
		if (qlFinder.isNativeSQL()) {// 如果是原生SQL语句则判断是否采用属性复制的方式
			query = createQuery(qlFinder.getQL(), qlFinder.isNativeSQL());
			query.unwrap(SQLQuery.class).setResultTransformer(new MyResultTransformer(clz));
		} else {
			query = createQuery(qlFinder.getQL(), qlFinder.isNativeSQL(), clz);
		}
		qlFinder.setParameterToQuery(query);
		setPageParameterToQuery(query, pageRequest);
		content = (List<T>) query.getResultList();
		return new PageImpl<>(content, pageRequest, count);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<T> findList(QLFinder qlFinder, Class clz) {
		List<T> result = new ArrayList<>();
		Query query = null;
		if (qlFinder.isNativeSQL()) {// 如果是原生SQL语句则判断是否采用属性复制的方式
			query = createQuery(qlFinder.getQL(), qlFinder.isNativeSQL());
			query.unwrap(SQLQuery.class).setResultTransformer(new MyResultTransformer(clz));
		} else {
			query = createQuery(qlFinder.getQL(), qlFinder.isNativeSQL(), clz);
		}
		qlFinder.setParameterToQuery(query);
		result = (List<T>) query.getResultList();
		entityManager.clear();// 分离内存中受EntityManager管理的实体bean，让VM进行垃圾回
		return result;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
	 */
	private Query setPageParameterToQuery(final Query q, final Pageable pageable) {
		q.setFirstResult(pageable.getOffset());
		q.setMaxResults(pageable.getPageSize());
		return q;
	}

	/**
	 * 根据查询JPQL与参数列表创建Query对象.
	 */
	@SuppressWarnings("rawtypes")
	private Query createQuery(final String queryString, final boolean nativeSQL, Class clz) {
		Query query = null;
		if (nativeSQL) {
			query = entityManager.createNativeQuery(queryString, clz);
		} else {
			query = entityManager.createQuery(queryString);
		}
		return query;
	}

	/**
	 * 根据查询JPQL与参数列表创建Query对象.
	 */
	private Query createQuery(final String queryString, final boolean nativeSQL) {
		Query query = null;
		if (nativeSQL) {
			query = entityManager.createNativeQuery(queryString);
		} else {
			query = entityManager.createQuery(queryString);
		}
		return query;
	}

	/**
	 * 通过count查询获得本次查询所能获得的对象总数.
	 * 
	 * @return
	 */
	private long countQLResult(final QLFinder qlFinder, final boolean nativeSQL) {
		Query query = createQuery(qlFinder.getRowCountQL(), nativeSQL);
		qlFinder.setParameterToQuery(query);
		try {
			Long count = 0L;
			if (nativeSQL) {// 原生SQL
				count = Long.parseLong(query.getSingleResult().toString());
			} else {
				count = (Long) query.getSingleResult();
			}
			return count;
		} catch (Exception e) {
			throw new RuntimeException("QL can't be auto count, QL is:" + qlFinder.getRowCountQL(), e);
		} finally {
			entityManager.clear();// 分离内存中受EntityManager管理的实体bean，让VM进行垃圾回收
		}
	}

	/**
	 * 创建排序
	 * 
	 * @param orders
	 * @return
	 */
	private Sort buildSort(Order... orders) {
		Sort sort = null;
		if (orders != null) {
			for (Order order : orders) {
				if (order != null) {
					if (sort == null) {
						sort = new Sort(order);
					} else {
						sort = sort.and(new Sort(order));
					}
				}
			}
		}
		return sort;
	}

	/**
	 * 获取排序
	 * 
	 * @param sort
	 * @return
	 */
	private Order[] getOrders(Sort sort) {
		if (sort != null) {
			List<Order> orderList = new ArrayList<>();
			if (sort != null) {
				for (Order order : sort) {
					orderList.add(order);
				}
			}
			if (!orderList.isEmpty()) {
				int size = orderList.size();
				Order[] orders = new Order[size];
				for (int i = 0; i < size; i++) {
					orders[i] = orderList.get(i);
				}
				return orders;
			}
		}

		return new Order[0];
	}

}