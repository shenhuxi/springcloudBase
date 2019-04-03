package com.dingxin.oauth2.userdetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.dingxin.common.vo.system.SysUserVo;

/**
 * 
* Title: DxUserDetails 
* Description:  自定义UserDetails实现
* @author dicky  
* @date 2018年6月27日 上午9:30:45
 */
public class DxUserDetails implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id; // 用户id

	private Long orgId; // 组织机构id

	private Long identId;//身份ID
	
	private String identName;//身份名称
	
	private String username;

	private String password;
	
	private Integer deleteState = 0;	//删除状态（0-正常,1-删除）
	
	private Integer userState = 0; //用户状态:0-停用,1-启用,2-注销
	
	private Collection<? extends GrantedAuthority> authorities = new HashSet<>();//权限资源集合

	public DxUserDetails(SysUserVo vo,Collection<? extends GrantedAuthority> authorities) {
		this.id = vo.getId();
		this.identId = vo.getIdentId();
		this.identName = vo.getIdentName();
		this.orgId = vo.getOrgId();
		this.username = vo.getUserName();
		this.password = vo.getPassWord();
		this.deleteState = vo.getDeleteState();
		this.userState = vo.getUserState();
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}


	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public Long getIdentId() {
		return identId;
	}

	public void setIdentId(Long identId) {
		this.identId = identId;
	}

	public String getIdentName() {
		return identName;
	}

	public void setIdentName(String identName) {
		this.identName = identName;
	}


	
}