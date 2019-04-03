package com.dingxin.system.service.permissionrole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.system.entity.SysPermission;
import com.dingxin.system.entity.SysPermissionRole;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.repository.permission.SysPermissionRepository;
import com.dingxin.system.repository.permissionrole.SysPermissionRoleRepository;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysRolePermissionService extends CommonService<SysPermissionRole, Long> {

	@Autowired
	private SysPermissionRoleRepository sysPermissionRoleRepository;

	@Autowired
	private SysPermissionRepository sysPermissionRepository;

	@Override
	public CommonRepository<SysPermissionRole, Long> getCommonRepository() {
		return sysPermissionRoleRepository;
	}

	public List<SysPermissionRole> findByRoleId(Long id) {
		return sysPermissionRoleRepository.findByRoleId(id);
	}

	public List<SysPermissionRole> findByPermissionId(Long id) {
		return sysPermissionRoleRepository.findByPermissionId(id);
	}

	public void binding(Long roleId, String sysPermissionIds) {
		String[] ids = sysPermissionIds.split(",");
		for (String id : ids) {
			SysPermissionRole sysPermissionRole = new SysPermissionRole();
			sysPermissionRole.setRoleId(roleId);
			sysPermissionRole.setPermissionId(Long.parseLong(id));
			sysPermissionRoleRepository.save(sysPermissionRole);
		}
	}

	public void updateRolePer(Long roleId, String sysPermissionIds) throws Exception{
		List<SysPermissionRole> sprs = sysPermissionRoleRepository.findByRoleId(roleId);
		if (!CollectionUtils.isEmpty(sprs)) {
			sysPermissionRoleRepository.delete(sprs);
		}
		if (sysPermissionIds == null) {
			return;
		}
		String[] ids = sysPermissionIds.split(",");
		for (String id : ids) {
			SysPermissionRole sysPermissionRole = new SysPermissionRole();
			sysPermissionRole.setRoleId(roleId);
			sysPermissionRole.setPermissionId(Long.parseLong(id));
			sysPermissionRoleRepository.save(sysPermissionRole);
		}
	}

	public void deleteRolePer(Long roleId, String sysPermissionIds) {
		String[] pids = sysPermissionIds.split(",");
		List<String> list = Arrays.asList(pids);
		List<SysPermissionRole> sprs = sysPermissionRoleRepository.findByRoleId(roleId);
		if (!CollectionUtils.isEmpty(sprs)) {
			for (SysPermissionRole spr : sprs) {
				String pid = String.valueOf(spr.getPermissionId());
				if (list.contains(pid)) {
					sysPermissionRoleRepository.deleteByRoleIdAndPermissionId(roleId, Long.parseLong(pid));
				}
			}
		}
	}

	public List<SysPermission> findPerByRoleId(Long id) {
		List<SysPermissionRole> sprs = sysPermissionRoleRepository.findByRoleId(id);
		List<Long> list = new ArrayList<>();
		if (!CollectionUtils.isEmpty(sprs)) {
			for (SysPermissionRole spr : sprs) {
				long pid = spr.getPermissionId();
				list.add(pid);
			}
			return sysPermissionRepository.findAll(list);
		}
		return Collections.emptyList();
	}

}
