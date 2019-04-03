package com.dingxin.system.service.userorganrole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysUserorganRole;
import com.dingxin.system.repository.userorganrole.SysUserorganRoleRepository;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysUserorganRoleService extends CommonService<SysUserorganRole,Long>{
    
	@Autowired
	 private SysUserorganRoleRepository userorganRoleRepository;


	@Override
	public CommonRepository<SysUserorganRole, Long> getCommonRepository() {
		return userorganRoleRepository;
	}


	public List<SysUserorganRole> findBySysRoleId(Long id) {
		return userorganRoleRepository.findBySysRoleId(id);
	}


	public void updateIdentRoles(Long userorganId, String roleIds) {
		List<SysUserorganRole> suors = userorganRoleRepository.findBySysUserorganId(userorganId);
		if (!CollectionUtils.isEmpty(suors)) {
			userorganRoleRepository.delete(suors);
		}
		if (roleIds == null) {
			return;
		}
		String[] ids = roleIds.split(",");
		List <SysUserorganRole> list=new ArrayList<>();
		for (String id : ids) {
			SysUserorganRole sysUserorganRole = new SysUserorganRole();
			sysUserorganRole.setSysUserorganId(userorganId);
			sysUserorganRole.setSysRoleId(Long.parseLong(id));
			list.add(sysUserorganRole);
		}
		userorganRoleRepository.save(list);
	}


	/**
	 * 根据身份ID删除身份与角色关联关系
	 * @param sysUserOranId
	 */
	public int deleteBySysUserorganId(Long sysUserOranId) {
		return userorganRoleRepository.deleteBySysUserorganId(sysUserOranId);
	}


	/**
	 * 保存多个实体
	 * @param entities
	 */
	public void saveList(List<SysUserorganRole> entities) {
		userorganRoleRepository.save(entities);
		
	}


	public List<SysUserorganRole> findBySysUserorganId(Long identId) {
		return userorganRoleRepository.findBySysUserorganId(identId);
	}
}
