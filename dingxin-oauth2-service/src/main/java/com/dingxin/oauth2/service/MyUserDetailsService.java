package com.dingxin.oauth2.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.entity.security.MyGrantedAuthority;
import com.dingxin.common.util.JSONUtils;
import com.dingxin.common.vo.system.SysPermissionVo;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.oauth2.SecurityConstants;
import com.dingxin.oauth2.userdetails.DxUserDetails;
import com.dingxin.system.rpc.SysPermissionServiceFeign;
import com.dingxin.system.rpc.SysUserServiceFeign;

/**
 * 
* Title: MyUserDetailsService 
* Description:  
* @author dicky  
* @date 2018年6月29日 上午9:46:43
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private SysUserServiceFeign userServiceFeign;

	@Autowired
	private SysPermissionServiceFeign permissionServiceFeign;

	/**
	 * 根据用户名获取登录用户信息
	 * 
	 * @param username
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String userstr = userServiceFeign.findByUserName(username);
		SysUserVo user = JSONUtils.toBean(userstr, SysUserVo.class);
		boolean isEnableUser = this.checkUserIsEnabled(user,username);
		if (user != null && isEnableUser) {
			List<SysPermissionVo> permissions = permissionServiceFeign.getPermissionsByUserId(user.getId());
			List<GrantedAuthority> grantedAuthorities = this.permissions2Authorities(permissions);
			return new DxUserDetails(user, grantedAuthorities);
		} else {
			throw new UsernameNotFoundException( username + SecurityConstants.USER_NOT_EXIST);
		}
	}

	/**
	 * 转换权限
	 * @param permissions
	 * @return
	 */
	private List<GrantedAuthority> permissions2Authorities(List<SysPermissionVo> permissions) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (permissions != null) {
			for (SysPermissionVo permission : permissions) {
				if (permission != null && permission.getName() != null) {
					MyGrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(),
							permission.getMethod());
					grantedAuthorities.add(grantedAuthority);
				}
			}
		}
		return grantedAuthorities;
	}

	/**
	 * 校验改用户是否可用
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	private boolean checkUserIsEnabled(SysUserVo user,String username) {
		boolean isEnableUser = false;
		String errorMsg = "";
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		try {
			String lock ="";
			lock = (String) valueOperations.get(username + RedisKeyConstant.USER_LOCK_KEY);
			if ("true".equals(lock)) {
				throw new AuthenticationServiceException(SecurityConstants.USER_LOCKED_STATE);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new AuthenticationServiceException(e.getMessage());
		}
		
		if(user != null) {
			if (user.getDeleteState() == CommonConstant.DELETE) {
				errorMsg = SecurityConstants.USER_DELETED_STATE;
			}
			if (user.getUserState() == CommonConstant.STOP_STATE) {
				errorMsg = SecurityConstants.USER_STOPPED_STATE;
			}
			if (user.getUserState() == CommonConstant.DESTROY_STATE) {
				errorMsg = SecurityConstants.USER_DESTROYED_STATE;
			}
			isEnableUser = true;
		} 
		if(!StringUtils.isEmpty(errorMsg)) {
			log.error(errorMsg);
			throw new AuthenticationServiceException(errorMsg);
		}
		return isEnableUser;
	}

	/**
	 * @param username 用户名
	 * @param identId 身份ID
	 * @param token 用户对应的token
	 * @return
	 * @throws Exception 
	 */
	public UserDetails loadUserByUsernameAndIndentid(String username, String identId, String token) throws UsernameNotFoundException {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		String json_User = "";
		try {
			json_User = (String) valueOperations.get(token+RedisKeyConstant.USER_KEY);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		SysUserVo userVo = JSONUtils.toBean(json_User,SysUserVo.class);
		if(userVo == null || userVo.getIdentName() ==null || !userVo.getUserName().equals(username)) {
			throw new UsernameNotFoundException( username + SecurityConstants.IDENTITY_SWITCH_FAILURE);
		}
		String userStr = userServiceFeign.findByUserName(username);
		SysUserVo user = JSONUtils.toBean(userStr, SysUserVo.class);
		boolean isEnableUser = this.checkUserIsEnabled(user,username);
		if (user != null && isEnableUser) {
			List<SysPermissionVo> permissions = permissionServiceFeign.getPermissionsByIdentId(Long.parseLong(identId));
			List<GrantedAuthority> grantedAuthorities = this.permissions2Authorities(permissions);
			return new DxUserDetails(user, grantedAuthorities);
		} else {
			throw new UsernameNotFoundException( username + SecurityConstants.USER_NOT_EXIST);
		}
	}

	/**
	 * 根据手机号码获取用户信息
	 * @param mobile
	 * @return
	 * @throws UsernameNotFoundException
	 */
	public UserDetails loadUserByMobile(String mobile) throws UsernameNotFoundException {
		String userstr = userServiceFeign.findByMobile(mobile);
		SysUserVo user = JSONUtils.toBean(userstr, SysUserVo.class);
		boolean isEnableUser = this.checkUserIsEnabled(user,mobile);
		if (user != null && isEnableUser) {
			List<SysPermissionVo> permissions = permissionServiceFeign.getPermissionsByUserId(user.getId());
			List<GrantedAuthority> grantedAuthorities = this.permissions2Authorities(permissions);
			return new DxUserDetails(user, grantedAuthorities);
		} else {
			throw new UsernameNotFoundException( "手机号码为："+mobile + SecurityConstants.USER_NOT_EXIST);
		}
	}

}
