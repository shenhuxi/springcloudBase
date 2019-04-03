package com.dingxin.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dingxin.oauth2.properties.DxSecurityProperties;



/**
 * 
* Title: SecurityCoreConfig 
* Description:  
* @author dicky  
* @date 2018年6月26日 下午2:18:01
 */

@Configuration
@EnableConfigurationProperties(DxSecurityProperties.class)   //配置读取器，让这些类生效
public class DxSecurityCoreConfig {

}
