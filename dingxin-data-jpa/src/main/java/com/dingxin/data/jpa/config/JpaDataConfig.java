package com.dingxin.data.jpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import com.dingxin.data.jpa.repository.MyRepositoryFactoryBean;



@Configuration  
@EnableJpaRepositories(basePackages = "com.dingxin.**.repository",   
                            repositoryFactoryBeanClass = MyRepositoryFactoryBean.class)  
@EnableSpringDataWebSupport  
public class JpaDataConfig {  
  
}  