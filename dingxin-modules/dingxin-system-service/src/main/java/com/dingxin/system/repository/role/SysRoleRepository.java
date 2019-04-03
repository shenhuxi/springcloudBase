package com.dingxin.system.repository.role;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysRole;

@Repository
public interface SysRoleRepository extends CommonRepository<SysRole,Long> {

	List<SysRole> findByName(String name);

	List<SysRole> findByNameAndIdNotIn(String name, Long id);

	/**
	 * @param identid
	 * @return
	 */
	@Query(value = "SELECT  r.*FROM sys_role r ,sys_userorgan_role a WHERE 1=1 AND a.sys_role_id=r.id AND a.sys_userorgan_id=:identid ",nativeQuery = true)
	List<SysRole> findRolesByIndentId(@Param("identid")Long identid);

	SysRole findById(Long rid);

	List<SysRole> findByIdIn(List<Long> rids);


}
