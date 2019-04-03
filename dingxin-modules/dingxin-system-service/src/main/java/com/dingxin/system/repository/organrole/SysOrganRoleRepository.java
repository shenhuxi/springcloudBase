package com.dingxin.system.repository.organrole;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysOrganRole;
import com.dingxin.system.entity.SysRole;


@Repository
public interface SysOrganRoleRepository extends CommonRepository<SysOrganRole,Long> {

	List<SysOrganRole> findBySysOrganId(@Param("orgId") long orgId);

	List<SysOrganRole> findBySysOrganId(Long sysOrganId);	
}
