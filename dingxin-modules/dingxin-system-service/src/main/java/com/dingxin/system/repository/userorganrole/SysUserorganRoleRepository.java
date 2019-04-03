package com.dingxin.system.repository.userorganrole;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysUserorganRole;


@Repository
public interface SysUserorganRoleRepository extends CommonRepository<SysUserorganRole,Long> {

	List<SysUserorganRole> findBySysRoleId(Long id);

	List<SysUserorganRole> findBySysUserorganId(Long userorganId);

	/**根据身份ID删除身份与角色关联关系
	 * @param sysUserOranId
	 */
	int deleteBySysUserorganId(Long sysUserOranId);
	
}
