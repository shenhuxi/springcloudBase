package com.dingxin.system.interceptor.config;

import com.dingxin.system.interceptor.argument.CurrentUserArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class RestParamsAdapter extends WebMvcConfigurerAdapter{
	
	@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        //登录用户作为参数传递 add shixh
        argumentResolvers.add(new CurrentUserArgumentResolver());
    }

}
