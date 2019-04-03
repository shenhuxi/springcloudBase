package com.dingxin.system.repository.userorgan;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysUserOrgan;


@Repository
public interface SysUserOrganRepository extends CommonRepository<SysUserOrgan,Long> {

	SysUserOrgan findById(long userOrgId);

	List<SysUserOrgan> findBySysUserId(Long sysUserId);

	/**
	 * 根据用户ID和机构ID查询身份关联信息
	 * @param userid
	 * @param orgId
	 * @return
	 */
	SysUserOrgan findBySysUserIdAndSysOrganId(Long userid, Long orgId);
	
	
	
}
