package com.dingxin.system.repository.organ;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysOrgan;

/**
 * SysOrgRepository
 * 
 * @author shixh
 */
@Repository
public interface SysOrganRepository extends CommonRepository<SysOrgan, Long> {

	List<SysOrgan> findByOrgNameOrOrgCode(String orgName, String orgCode);

	@Query("from SysOrgan where (orgName =:orgName or orgCode=:orgCode) and id not in (:id)")
	List<SysOrgan> findByOrgNameOrOrgCodeAndIdNotIn(@Param("orgName") String orgName, @Param("orgCode") String orgCode,
			@Param("id") Long id);

	List<SysOrgan> findByParentId(@Param("parentId") Long parentId);

	SysOrgan findById(Long parentId);

	List<SysOrgan> findByParentIdAndIdNotIn(@Param("parentId") Long parentId, @Param("Id") Long id);

	void deleteByIdIn(List<Long> longList);

	List<SysOrgan> findByParentIdAndIdNotIn(long parentId, List<Long> longList);

	List<SysOrgan> findByParentIdIn(List<Long> ids);

	/*
	 *  * @Author  Luozb
	 * @desc 上生产环境或预发布环境时须记得将以上语句导入到mysql中执行一遍。
	 * 
	 * 此处用自定义函数完成，建立函数语句脚本如下，适合于mysql树状结构的数据查询。当然也可以采用子查询语句或多次查询方法完成，但当层级特别多时，子查询语句效率不高。
	 CREATE FUNCTION `getChildLst`(rootId INT) 
	 RETURNS varchar(1000)
	 BEGIN 
	 DECLARE sTemp VARCHAR(1000); 
	 DECLARE sTempChd VARCHAR(1000);
	 SET sTemp = '$'; 
	 SET sTempChd =cast(rootId as CHAR);	 * 
	 WHILE sTempChd is not null DO 
	 SET sTemp = concat(sTemp,',',sTempChd); 
	 SELECT group_concat(id) INTO sTempChd FROM sys_organ where FIND_IN_SET(parent_id,sTempChd)>0; 
	 END WHILE; 
	 RETURN sTemp; 
	 END
	 * 	
	 * 
	 * 通用的查询语句有： 
	 * 1.select getChildLst(1);
	 * 
	 * 2.select * from sys_organ where FIND_IN_SET(id, getChildLst(3));
	 * 
	 * 3.select id,org_level,parent_id from sys_organ where id in(select id from sys_organ where FIND_IN_SET(id, getChildLst(1)));
	 * 
	 * 4.select id,org_level,parent_id from sys_organ where id in(select id from sys_organ where FIND_IN_SET(id, getChildLst(2)) )and org_level<4;
	 * 
	 * 
	 */
	@Query(value = "select id from sys_organ where id in(select id from sys_organ where FIND_IN_SET(id, getChildLst( ?1))) and org_level<= ?2", nativeQuery = true)
	List<Long> findOrgsByIdAndLayer(@Param("curOrgId") Long curOrgId, @Param("orgLevel") int orgLevel);

}
