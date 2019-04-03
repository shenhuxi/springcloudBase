package com.dingxin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dingxin.properties.DxApiGateWateProperties;

/**
 * 
* Title: ApiGateWayCoreConfig 
* Description: 服务网关核心自定义配置类
* @author dicky  
* @date 2018年6月26日 下午2:18:01
 */

@Configuration
@EnableConfigurationProperties(DxApiGateWateProperties.class)   //配置读取器，让这些类生效
public class ApiGateWayCoreConfig {

}
