package com.dingxin.system.service.organrole;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysLayerManager;
import com.dingxin.system.entity.SysOrgan;
import com.dingxin.system.entity.SysOrganRole;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.entity.SysUserOrgan;
import com.dingxin.system.repository.organrole.SysOrganRoleRepository;
import com.dingxin.system.service.layer.SysLayerManagerService;
import com.dingxin.system.service.organ.SysOrganService;
import com.dingxin.system.service.role.SysRoleService;
import com.dingxin.system.service.userorgan.SysUserOrganService;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysOrganRoleService extends CommonService<SysOrganRole, Long> {
	@Autowired
	private SysUserOrganService sysUserOrganService;
	@Autowired
	private SysLayerManagerService sysLayerManagerService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysOrganService organService;
	@Autowired
	private SysOrganRoleRepository organRoleRepository;

	@Override
	public CommonRepository<SysOrganRole, Long> getCommonRepository() {
		return organRoleRepository;
	}

	public List<SysOrganRole> findRolesByOrgId(long orgId) {
		return organRoleRepository.findBySysOrganId(orgId);

	}

	/**
	 * @Description 根据登录用户获取用户所拥有的角色，如果是超级管理员，则可授予任意机构，任意用户（原则上只授予顶级机构，由顶级机构的分级管理员根据直辖管理层数向下授权）。
	 *              1.根据登录用户获取用户的身份. 
	 *              2.根据用户身份获取该身份所拥有的角色
	 *              3.如果该身份包含超级管理员角色，则可任意授权,返回整个组织机构。
	 *              4.如果包含了分级管理员角色，则根据该身份查询分级管理员表，得到直辖管理层数。 根据直辖管理层数的配置，返回对应的机构树。
	 *              5.如果是普通用户，则返回无授权权限！
	 * 
	 * @author luozb
	 */
	public List<TreeBean> getLoginUserAuth(SysUserVo loginUser) {
		Long curUserId = loginUser.getId();
		Long curOrgId = loginUser.getOrgId();
		SysUserOrgan suo = sysUserOrganService.findByUserIdAndOrgId(curUserId, curOrgId);
		List<SysOrgan> soes = new ArrayList<>();
		List<String> list = new ArrayList<>();
		List<SysRole> srs = sysRoleService.findRolesByIndentId(suo.getId());
		if (!CollectionUtils.isEmpty(srs)) {
			for (SysRole sr : srs) {
				String roleName = sr.getName();
				list.add(roleName);
			}
		} else {
			return null;
		}
		if (list.contains("超级管理员")) {
			List<SysOrgan> sysOrganmins = organService.findAll();
			soes = sysOrganmins;
		} else if (list.contains("分级管理员")) {
			SysLayerManager slm = sysLayerManagerService.findBySysOrganIdAndSysUserId(curOrgId, curUserId);
			int layer = slm.getLayer();
			switch (layer) {
			case 0:
				// 只查当前
				SysOrgan sysOrgan0 = organService.findById(curOrgId);
				soes.add(sysOrgan0);
				break;
			case 1:
				// 查当前和下一级
				SysOrgan sysOrgan1 = organService.findById(curOrgId);
				List<SysOrgan> sysOrgans1 = organService.findByParentId(curOrgId);
				sysOrgans1.add(sysOrgan1);
				soes = sysOrgans1;
				break;
			case 2:
				// 查当前,下一级,下两级
				SysOrgan sysOrgan2 = organService.findById(curOrgId);
				List<SysOrgan> sysOrgans2 = organService.findByParentId(curOrgId);
				List<Long> ids = new ArrayList<>();
				for (SysOrgan soo : sysOrgans2) {
					Long id = soo.getId();
					ids.add(id);
				}
				List<SysOrgan> sysOrgs2 = organService.findOrgsByParentId(ids);
				sysOrgans2.addAll(sysOrgs2);
				sysOrgans2.add(sysOrgan2);
				soes = sysOrgans2;
				break;
			case 3:
				// 查当前,下一级,下两级及下三级机构
				SysOrgan curso = organService.findById(curOrgId);
				List<SysOrgan> sonexts1 = organService.findByParentId(curOrgId);
				List<Long> ids2 = new ArrayList<>();
				List<Long> ids3 = new ArrayList<>();
				for (SysOrgan sooo : sonexts1) {
					Long id = sooo.getId();
					ids2.add(id);
				}
				List<SysOrgan> sonexts2 = organService.findOrgsByParentId(ids2);
				for (SysOrgan soooo : sonexts2) {
					Long tid = soooo.getId();
					ids3.add(tid);
				}
				List<SysOrgan> sonexts3 = organService.findOrgsByParentId(ids3);
				sonexts1.addAll(sonexts2);
				sonexts1.addAll(sonexts3);
				sonexts1.add(curso);
				soes = sonexts1;
				break;
			case -1:
				// 查所有机构
				List<SysOrgan> sysOrganmins = organService.findAll();
				soes = sysOrganmins;
				break;
			default:
				break;
			}
		} else {
			return null;
		}
		return organService.searchAppointOrgTree(soes);

	}

	/**
	 * @Description 授权
	 *   根据登录用户获取用户所拥有的角色，如果是超级管理员，则可授予任意机构，任意用户（原则上只授予顶级机构，由顶级机构的分级管理员根据直辖管理层数向下授权）。
	 *              1.根据登录用户获取用户的身份. 
	 *              2.根据用户身份获取该身份所拥有的角色，如果为空，提示角色设置失败！当前登录用户尚未分配角色！
	 *              3.如果该身份包含超级管理员角色，则该用户拥有orgId=1且层数为4的授权范围,即从顶级节点到所有叶子节点，返回整个组织机构。
	 *              4.如果该身份包含了分级管理员角色，则根据该身份查询分级管理员表，得到直辖管理层数。 根据直辖管理层数的配置，返回对应的所有orgids。
	 *              5.如果是普通用户，则返回提示-角色设置失败！当前登录用户无管理员分配角色权限！！
	 *              6.如果orgids中包含了sysOrganId，则可以授权。否则提示-角色设置失败！不能对直辖管理范围外的权限授权！
	 *              7.授权。可实现增删改，当sysRoleIds=null时，为该机构删除所有角色。
	 * 
	 * @author luozb
	 */
	public ResultObject updateOrganRole(SysUserVo loginUser, Long sysOrganId, String sysRoleIds) {
		Long curUserId = loginUser.getId();
		Long curOrgId = loginUser.getOrgId();
		SysUserOrgan suo = sysUserOrganService.findByUserIdAndOrgId(curUserId, curOrgId);		
		List<String> roleNames = new ArrayList<>();
		List<SysRole> srs = sysRoleService.findRolesByIndentId(suo.getId());
		if (!CollectionUtils.isEmpty(srs)) {
			for (SysRole sr : srs) {
				String roleName = sr.getName();
				roleNames.add(roleName);
			}
		} else {
			return ResultObject.fail("角色设置失败！当前登录用户尚未分配角色！");
		}
		List<Long> orgIds = new ArrayList<>();
		if (roleNames.contains("超级管理员")) {
			curOrgId = 1L;
			int layer = 4;
			orgIds = organService.findOrgsByIdAndLayer(curOrgId, layer);
		} else if (roleNames.contains("分级管理员")) {
			SysLayerManager slm = sysLayerManagerService.findBySysOrganIdAndSysUserId(curOrgId, curUserId);
			int layer = slm.getLayer();
			if (layer == -1) {
				layer = 4;
			}
			orgIds = organService.findOrgsByIdAndLayer(curOrgId, layer);
		} else {
			return ResultObject.fail("角色设置失败！当前登录用户无管理员分配角色权限！");
		}
		List<String> sorgIds = new ArrayList<>();
		for (int i = 0; i < orgIds.size(); i++) {
			String sid = String.valueOf(orgIds.get(i));
			sorgIds.add(sid);
		}
		String soid = String.valueOf(sysOrganId);
		if (sorgIds.contains(soid)) {
			List<SysOrganRole> sors = organRoleRepository.findBySysOrganId(sysOrganId);
			if (!CollectionUtils.isEmpty(sors)) {
				organRoleRepository.delete(sors);
			}
			if (sysRoleIds == null) {
				return ResultObject.ok("角色删除成功！");
			}
			List<SysOrganRole> list = new ArrayList<>();
			String[] ids = sysRoleIds.split(",");
			for (String id : ids) {
				SysOrganRole sysOrganRole = new SysOrganRole();
				sysOrganRole.setSysOrganId(sysOrganId);
				sysOrganRole.setSysRoleId(Long.parseLong(id));
				list.add(sysOrganRole);
			}
			organRoleRepository.save(list);
			return ResultObject.ok("角色设置成功！", list);
		} else {
			return ResultObject.fail("角色设置失败！不能对直辖管理范围外的权限授权！");
		}
	}

}
