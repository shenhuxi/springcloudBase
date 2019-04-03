package com.dingxin.system.repository.permissionrole;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysPermissionRole;

@Repository
public interface SysPermissionRoleRepository extends CommonRepository<SysPermissionRole,Long> {

	List<SysPermissionRole> findByRoleId(Long id);

	List<SysPermissionRole> findByPermissionId(Long id);

	void deleteByRoleIdAndPermissionId(Long roleId, long sysPermissionId);

}
