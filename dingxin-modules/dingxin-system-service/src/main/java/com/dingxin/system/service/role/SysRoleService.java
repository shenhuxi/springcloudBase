package com.dingxin.system.service.role;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.system.entity.SysOrganRole;
import com.dingxin.system.entity.SysPermissionRole;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.entity.SysUserorganRole;
import com.dingxin.system.repository.role.SysRoleRepository;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.system.service.organrole.SysOrganRoleService;
import com.dingxin.system.service.permissionrole.SysRolePermissionService;
import com.dingxin.system.service.userorganrole.SysUserorganRoleService;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysRoleService extends CommonService<SysRole,Long>{
	
    @Autowired
    private SysRoleRepository sysRoleRepository;
    
	@Autowired
	private SysRolePermissionService sysRolePermissionService;
	
	@Autowired
	private SysUserorganRoleService sysUserorganRoleService;
	
	@Autowired
	private SysOrganRoleService organRoleService;
	@Override
	public CommonRepository<SysRole, Long> getCommonRepository() {
		return sysRoleRepository;
	}

	public SysRole checkAndSave(SysRole entity) throws RestException {
		List<SysRole> sysRoles = sysRoleRepository.findByName(entity.getName());
		if(!CollectionUtils.isEmpty(sysRoles)) {
            throw new RestException("角色名字重复！");
        }
		return sysRoleRepository.save(entity);
	}

	public SysRole checkAndUpdate(SysRole entity) throws RestException {
		List<SysRole> sysRoles = sysRoleRepository.findByNameAndIdNotIn(entity.getName(),entity.getId());
		if(!CollectionUtils.isEmpty(sysRoles)) {
            throw new RestException("角色名字重复！");
        }
		SysRole  sysRole = sysRoleRepository.findOne(entity.getId());
		BeanUtil.copyPropertiesIgnoreNull(entity, sysRole);
		sysRoleRepository.save(sysRole);
		return sysRole;
	}
    
	public void deleteById(Long id) throws RestException, IllegalArgumentException, IllegalAccessException  {
        SysRole sysRole = findOne(id);
        if(sysRole==null) {
            throw new RestException("角色数据不存在，删除失败!");
        }
        List<SysPermissionRole> sysPermissionRoles = sysRolePermissionService.findByRoleId(id);
        if(!CollectionUtils.isEmpty(sysPermissionRoles)) {
            throw new RestException("该角色下面还有权限数据 ，操作失败!");
        }
        sysRoleRepository.delete(sysRole);
        List<SysUserorganRole> syors = sysUserorganRoleService.findBySysRoleId(id);
        if(!CollectionUtils.isEmpty(syors)) {
        	sysUserorganRoleService.delete(syors);
        }
	}

	/**根据身份ID获取角色
	 * @param identid
	 * @return
	 */
	public List<SysRole> findRolesByIndentId(Long identid) {
		return sysRoleRepository.findRolesByIndentId(identid);
	}

	public List<SysRole> findRolesByOrgId(long orgId) {		
		List<SysOrganRole> list= organRoleService.findRolesByOrgId(orgId);
		List<SysRole> srs=new ArrayList<>();		
		for(SysOrganRole sor:list) {
			Long rid=sor.getSysRoleId();
			SysRole sr=sysRoleRepository.findById(rid);
			srs.add(sr);
		}
		return srs;
	}

	public List<SysRole> findByIdIn(List<Long> rids) {
		return sysRoleRepository.findByIdIn(rids);
	}


}

