package com.dingxin.system.service.roleuser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysRoleUser;
import com.dingxin.system.repository.roleuser.SysRoleUserRepository;

@Service
public class SysRoleUserService extends CommonService<SysRoleUser,Long>{

	@Autowired
	private SysRoleUserRepository sysRoleUserRepository;
	
	@Override
	public CommonRepository<SysRoleUser, Long> getCommonRepository() {
		return sysRoleUserRepository;
	}

	public List<SysRoleUser> findBySysUserIdAndSysRoleId(Long sysUserId, Long sysRoleId) { return sysRoleUserRepository.findBySysUserIdAndSysRoleId(sysUserId,sysRoleId);
	}
	public int deleteBySysUserId(Long sysUserId) {
		return sysRoleUserRepository.deleteBySysUserId(sysUserId);
	}

	/**
	 * 保存多个实体
	 * @param entities
	 */
	public void saveList(List<SysRoleUser> entities) {
		sysRoleUserRepository.save(entities);
	}
}
