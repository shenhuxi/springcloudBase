package com.dingxin.system.service.userorgan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysUserOrgan;
import com.dingxin.system.repository.organ.SysOrganRepository;
import com.dingxin.system.repository.userorgan.SysUserOrganRepository;

@Service
public class SysUserOrganService extends CommonService<SysUserOrgan, Long> {

	@Autowired
	private SysUserOrganRepository userOrganRepository;

	@Autowired
	private SysOrganRepository sysOrganRepository;

	@Override
	public CommonRepository<SysUserOrgan, Long> getCommonRepository() {
		return userOrganRepository;
	}

	public SysUserOrgan findById(long userOrgId) {
		return userOrganRepository.findById(userOrgId);
	}

	/**
	 * 根据用户ID获取身份集合
	 * @param userid
	 * @param orgid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SysUserOrganVo> findSysUserOrganVoListByUserId(Long userid, Long orgid) {
		List<SysUserOrganVo> resultList = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT a.id ,org.org_name orgName,org.org_code orgCode,org_phone orgPhone,a.sys_organ_id orgId FROM sys_organ org ,sys_user_organ a WHERE 1=1 AND a.sys_organ_id=org.id ");
		Map<String, Object>  searchParams = new HashMap<>();
		searchParams.put("EQ_a.sysUserId", userid);
		resultList = sysOrganRepository.findListByNativeSQLAndParams(sql.toString(), searchParams, SysUserOrganVo.class, new Sort(Sort.Direction.DESC, "id"));
		if(resultList!=null && !resultList.isEmpty()) {
			for (SysUserOrganVo sysUserOrganVo : resultList) {
				if(sysUserOrganVo.getOrgId().longValue() == orgid) {
					sysUserOrganVo.setIsDefault("0");
				}
			}
		}
		return resultList;
	}
	
	/**
	 * 保存和修改用户与身份的关系
	 * @param userId
	 * @param sysOrganIds
	 */
	public void updateUserOrgan(Long userId, String sysOrganIds) {
		List<SysUserOrgan> identList = userOrganRepository.findBySysUserId(userId);//查询用户当前所拥有的身份
		String[] ids = sysOrganIds.split(",");
		List<SysUserOrgan> list = new ArrayList<>();
		for (String id : ids) {
			SysUserOrgan sysUserOrgan = null;
			boolean flag = false;
			if (!CollectionUtils.isEmpty(identList)) {
				for (SysUserOrgan ident : identList) {
					Long sysOrgId = ident.getSysOrganId();
					if(sysOrgId.toString().equals(id)) {
						sysUserOrgan = ident;
						flag = true;
						break;
					}
				}
			}
			if(!flag) {
				sysUserOrgan = new SysUserOrgan();
			}
			sysUserOrgan.setSysUserId(userId);
			sysUserOrgan.setSysOrganId(Long.parseLong(id));
			list.add(sysUserOrgan);
		}
		userOrganRepository.save(list);
	}

	/**
	 * 根据用户ID和机构ID查询身份关联信息
	 * 
	 * @param userid
	 * @param orgId
	 * @return
	 */
	public SysUserOrgan findByUserIdAndOrgId(Long userid, Long orgId) {
		return userOrganRepository.findBySysUserIdAndSysOrganId(userid, orgId);
	}

	/**
	 * 获取用户身份信息
	 * @param userid 用户ID
	 * @param orgid 机构ID
	 * @return
	 */
	public SysUserOrganVo findUserOrganVoByUserId(Long userid, Long orgid) {
		List<SysUserOrganVo> list = this.findSysUserOrganVoListByUserId(userid, orgid);
		SysUserOrganVo vo = null;
		for (SysUserOrganVo sysUserOrganVo : list) {
			if(sysUserOrganVo.getOrgId().longValue() == orgid) {
				vo = sysUserOrganVo;
				break;
			}
		}
		return vo;
	}
	
	/**
	 * 根据身份ID查询身份信息
	 * @param identid
	 * @param orgid
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public SysUserOrganVo findUserOrganVoByIdentId(Long identid, Long orgid) {
		List<SysUserOrganVo> list = new ArrayList<>();
		StringBuilder sql = new StringBuilder("SELECT a.id ,org.org_name orgName,org.org_code orgCode,org_phone orgPhone,a.sys_organ_id orgId FROM sys_organ org ,sys_user_organ a WHERE 1=1 AND a.sys_organ_id=org.id ");
		Map<String, Object>  searchParams = new HashMap<>();
		searchParams.put("EQ_a.id", identid);
		list = sysOrganRepository.findListByNativeSQLAndParams(sql.toString(), searchParams, SysUserOrganVo.class, new Sort(Sort.Direction.DESC, "id"));
		SysUserOrganVo vo = null;
		if(list != null && !list.isEmpty()) {
			vo = list.get(0);
			if(vo.getOrgId().longValue() == orgid) {
				vo.setIsDefault("0");
			}
		}
		return vo;
	}

}
