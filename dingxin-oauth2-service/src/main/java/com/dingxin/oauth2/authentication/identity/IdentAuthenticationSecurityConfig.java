/**
 * 
 */
package com.dingxin.oauth2.authentication.identity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.dingxin.oauth2.service.MyUserDetailsService;





/**
 * 
* Title:  IdentAuthenticationSecurityConfig
* Description:  身份切换认证安全配置
* @author dicky  
* @date 2018年6月22日 下午7:16:34
 */
@Component
public class IdentAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailHandler;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		IdentAuthenticationFilter identAuthenticationFilter = new IdentAuthenticationFilter();
		identAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		identAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
		identAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
		IdentAuthenticationProvider identAuthenticationProvider = new IdentAuthenticationProvider();
		identAuthenticationProvider.setMyUserDetailsService(myUserDetailsService);
		
		http.authenticationProvider(identAuthenticationProvider)
			.addFilterAfter(identAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}
