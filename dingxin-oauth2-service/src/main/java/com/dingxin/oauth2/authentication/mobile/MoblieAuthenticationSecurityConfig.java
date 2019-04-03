/**
 * 
 */
package com.dingxin.oauth2.authentication.mobile;

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
* Title: MoblieAuthenticationSecurityConfig 
* Description:  手机登录认证安全配置
* @author dicky  
* @date 2018年6月22日 下午7:16:34
 */
@Component
public class MoblieAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailHandler;
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
		mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		mobileAuthenticationFilter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
		mobileAuthenticationFilter.setAuthenticationFailureHandler(myAuthenticationFailHandler);
		MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
		mobileAuthenticationProvider.setMyUserDetailsService(myUserDetailsService);
		
		http.authenticationProvider(mobileAuthenticationProvider)
			.addFilterAfter(mobileAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
	}

}
