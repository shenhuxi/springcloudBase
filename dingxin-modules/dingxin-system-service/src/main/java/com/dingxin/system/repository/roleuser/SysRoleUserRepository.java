package com.dingxin.system.repository.roleuser;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysRoleUser;

@Repository
public interface SysRoleUserRepository extends CommonRepository<SysRoleUser,Long> {

	List<SysRoleUser> findBySysUserIdAndSysRoleId(Long sysUserId, Long sysRoleId);
	
	int deleteBySysUserId(Long sysUserId);
}
