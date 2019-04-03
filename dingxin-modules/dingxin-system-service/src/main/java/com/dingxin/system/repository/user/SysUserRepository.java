package com.dingxin.system.repository.user;

import java.util.Collection;
import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.entity.SysUser;


@Repository
public interface SysUserRepository extends CommonRepository<SysUser,Long> {
	
	SysUser findByUserName(String name);

	SysUser findByUserNameAndPassWord(String userName, String passWord);
	
	@QueryHints(value = {@QueryHint(name ="sys_user",value = "sys_user hint")})
	@Query("from SysUser u where u.deleteState="+CommonConstant.NORMAL+" and u.orgId=:orgId")
	Page<SysUser> findByOrgId(@Param("orgId") long orgId,Pageable pageable);

	@QueryHints(value = {@QueryHint(name ="sys_user",value = "sys_user hint")})
	@Query("select distinct su from SysUser su, SysUserOrgan suo where su.id = suo.sysUserId and su.deleteState =0 and  suo.sysOrganId =:orgId")
	Page<SysUser> findByOrgIdWithRef(@Param("orgId") long orgId,Pageable pageable);
	
	List<SysUser> findByIdIn(Collection<Long> ids);
	
	List<SysUser> findByOrgId(long orgId);

	SysUser findByUserNameAndOrgIdAndDeleteState(String userName, Long orgId,int deleteState);

	@Query("select distinct r from  SysRole as  r, SysRoleUser as ru  , SysUser as u   where  r.id=ru.sysRoleId and ru.sysUserId= ?1")
	List<SysRole> findSysRoleBySysUserId(Long id);

	/**
	 * 根据手机号码查询用户
	 * @param mobile
	 * @return
	 */
	SysUser findByMobile(String mobile);
}
