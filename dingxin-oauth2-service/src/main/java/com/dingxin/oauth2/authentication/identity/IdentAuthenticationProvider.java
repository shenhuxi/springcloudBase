/**
 * 
 */
package com.dingxin.oauth2.authentication.identity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.dingxin.oauth2.service.MyUserDetailsService;





/**
 * 
* Title: IndentAuthenticationProvider 
* Description:  
* @author dicky  
* @date 2018年6月28日 下午3:25:39
 */
public class IdentAuthenticationProvider implements AuthenticationProvider {

	private MyUserDetailsService myUserDetailsService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		IdentAuthenticationToken authenticationToken = (IdentAuthenticationToken) authentication;
		String username = (String)authenticationToken.getPrincipal();//用户名
		String identid = (String)authenticationToken.getIdentid();//身份ID
		String token = (String)authenticationToken.getToken();//当前用户对应的token信息
		UserDetails user = myUserDetailsService.loadUserByUsernameAndIndentid(username,identid,token);
		if (user == null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		IdentAuthenticationToken authenticationResult = new IdentAuthenticationToken(user, user.getAuthorities());
		authenticationResult.setDetails(authenticationToken.getDetails());
		return authenticationResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return IdentAuthenticationToken.class.isAssignableFrom(authentication);
	}

	public MyUserDetailsService getMyUserDetailsService() {
		return myUserDetailsService;
	}

	public void setMyUserDetailsService(MyUserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}


}
