package com.dingxin.oauth2.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.dingxin.oauth2.SecurityConstants;
import com.dingxin.oauth2.authentication.identity.IdentAuthenticationSecurityConfig;
import com.dingxin.oauth2.authentication.mobile.MoblieAuthenticationSecurityConfig;
import com.dingxin.oauth2.properties.DxSecurityProperties;


/**
 * 
* Title: DxResourceServerConfig 
* Description:  资源服务器配置
* @author dicky  
* @date 2018年6月22日 下午10:56:22
 */
 
@Configuration
@EnableResourceServer
public class DxResourceServerConfig extends ResourceServerConfigurerAdapter {
	private static final String RESOURCE_ID = "my_rest_api";
	
	@Autowired
	private DxSecurityProperties dxSecurityProperties;
	
	@Autowired
	private AuthenticationSuccessHandler myAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler myAuthenticationFailHandler;
	
	@Autowired
	private MoblieAuthenticationSecurityConfig moblieAuthenticationSecurityConfig;//手机登录配置
	
	@Autowired
	private IdentAuthenticationSecurityConfig identAuthenticationSecurityConfig;//切换身份安全配置
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
	}
	
    @Override
    public void configure(HttpSecurity http) throws Exception {
    	ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
                .authorizeRequests();
    	 registry.anyRequest().authenticated()
         .and()
         .csrf().disable();
        for (String url : dxSecurityProperties.getUrls().getNoAuth()) {
            registry.antMatchers(url).permitAll();
        }
        http
        	.apply(moblieAuthenticationSecurityConfig)
        	.and()
        	.apply(identAuthenticationSecurityConfig)
        	.and()
        	.formLogin().loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL)
        	.successHandler(myAuthenticationSuccessHandler)
        	.failureHandler(myAuthenticationFailHandler)
        	.and()
            .exceptionHandling()
            .authenticationEntryPoint((request, response, authException) -> 
            	response.sendError(HttpServletResponse.SC_UNAUTHORIZED));
        
    }
}
