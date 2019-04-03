package com.dingxin.data.jpa.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.dingxin.data.jpa.repository.CommonRepository;


/**
 *
 * Create By qinzhw
 * 2018年5月8日上午11:11:42
 */
public abstract class CommonService<E,ID extends Serializable>  {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public abstract CommonRepository<E, ID> getCommonRepository();
	
	/**
	 * 根据ID获取某个Entity
	 * @param id
	 * @return
	 */
	public E get(ID id) {
		return getCommonRepository().getOne(id);
	}

	/**
	 * 根据ID查找某个Entity（建议使用）
	 * @param id
	 * @return
	 */
	public E findOne(ID id)  {
		E e = getCommonRepository().findOne(id);
		return e;
	}

	/**
	 * 获取所有的Entity列表
	 * @return
	 */
	public List<E> getAll() {
		return getCommonRepository().findAll();
	}
	
	/**
	 * 获取Entity的总数
	 * @return
	 */
	public Long getTotalCount() {
		return getCommonRepository().count();
	}

	/**
	 * 保存Entity
	 * @param entity
	 * @return
	 */
	public E save(E entity){
		return getCommonRepository().save(entity);
	}

	
	/**
	 * 修改Entity
	 * @param entity
	 * @return
	 */
	public E update(E entity) {
		return getCommonRepository().save(entity);
	}
	
	/**
	 * 删除Entity
	 * @param entity
	 */
	public void delete(E entity) {
		getCommonRepository().delete(entity);
	}

	/**
	 * 根据Id删除某个Entity
	 * @param id
	 */
	public void delete(ID id) {
		try {
			getCommonRepository().delete(id);
		} catch (EmptyResultDataAccessException e) {
			//如果是ID在DB不存在,不往外抛异常 add shixh 0521
			logger.info("如果是ID在DB不存在,不往外抛异常");
		}
		
	}

	/**
	 * 删除Entity的集合类
	 * @param entities
	 */
	public void delete(Collection<E> entities) {
		getCommonRepository().delete(entities);
	}

	/**
	 * 清空缓存，提交持久化
	 */
	public void flush() {
		getCommonRepository().flush();
	}
	
	/**
	 * 根据查询信息获取某个Entity的列表
	 * @param spec
	 * @return
	 */
	public List<E> findAll(Specification<E> spec) {
		return getCommonRepository().findAll(spec);
	}
	
	/**
	 * 根据查询信息获取某个Entity的列表
	 * @param searchParams
	 * @return
	 */
	public List<E> findAll(Map<String, Object> searchParams) {
		return getCommonRepository().findListByParams(searchParams);
	}
	
	/**
	 * 获取Entity的分页信息
	 * @param searchParams
	 * @param pageRequest
	 * @return
	 */
	public Page<E> findAll(Map<String, Object> searchParams,PageRequest pageRequest){
		return getCommonRepository().findPageByParams(searchParams, pageRequest);
	}
	
	/**
	 * 获取Entity的分页信息
	 * @param pageable
	 * @return
	 */
	public Page<E> findAll(Pageable pageable){
		return getCommonRepository().findAll(pageable);
	}
	
	/**
	 * 根据查询条件和分页信息获取某个结果的分页信息
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<E> findAll(Specification<E> spec, Pageable pageable) {
		return getCommonRepository().findAll(spec, pageable);
	}
	
	/**
	 * 根据查询条件和排序条件获取某个结果集列表
	 * @param spec
	 * @param sort
	 * @return
	 */
	public List<E> findAll(Specification<E> spec, Sort sort) {
		return getCommonRepository().findAll(spec,sort);
	}
	
	/**
	 * 查询某个条件的结果数集
	 * @param spec
	 * @return
	 */
	public long count(Specification<E> spec) {
		return getCommonRepository().count(spec);
	}
	

	/**
	 * 根据SQL返回分页,支持多表查询
	 * @author shixh
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<E> getPage(String sql,int pageNum,int pageSize,Class clzss) {
		PageRequest pageRequest = new PageRequest(pageNum, pageSize,new Sort(Sort.Direction.DESC, "id"));
		return getCommonRepository().findPageByNativeSQL(sql, clzss, pageRequest);
	}
	
	/**
	 * 根据原生SQL返回分页
	 * @author shixh
	 * @param sql
	 * @param pageNum
	 * @param pageSize
	 * @param sort
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<E> getPage(String sql,int pageNum,int pageSize,Sort sort,Class clazz) {
		PageRequest pageRequest = new PageRequest(pageNum, pageSize,sort);
		return getCommonRepository().findPageByNativeSQL(sql, clazz, pageRequest);
	}
	
 
	/**
	 * 支持多个表字段 返回 一个 对象 
	 * @author shixh
	 * @param sql
	 * @param pageable
	 * @param clazz 返回对象类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<E> getPage(String sql,PageRequest pageable,Class clazz) {
		return getCommonRepository().findPageByNativeSQL(sql, clazz, pageable);
	}

}