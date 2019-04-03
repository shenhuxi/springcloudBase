//package com.dingxin.system.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
//
///**
// * 描述: swagger2 /v2/api-docs资源配置
// * 作者: qinzhw
// * 创建时间: 2018/5/24 17:12
// */
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http
//				//.anonymous().disable()  //禁止匿名
//                //.anyRequest().authenticated() 和 anonymous().disable()任一时，requestMatchers().antMatchers需要认证 生效
//                //.anyRequest().authenticated() 时 requestMatchers().antMatchers 需要完全权限
//                //.anonymous().disable()        时 requestMatchers().antMatchers 找不到认证对象
//                //两者全配置时：找不到认证对象；全不配置时：所有都能访问
////				.requestMatchers().antMatchers("/user*/**")  //需要认证的url
////				.and()
//                .authorizeRequests()
//				.antMatchers("/**").permitAll()
//                .anyRequest().authenticated()
//				.and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler())
//				.and().csrf().disable();    //取消csrf
//
////		http.
////				csrf().disable()
////				.exceptionHandling()
////				.and()
////				.authorizeRequests()
////				.mvcMatchers("/v2/api-docs").permitAll()
////				.anyRequest().authenticated()
////				.and()
////				.httpBasic();
//		//.anonymous().disable() //禁止匿名
//	}
//
//}
