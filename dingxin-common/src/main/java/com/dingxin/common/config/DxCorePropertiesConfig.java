/**
 * 
 */
package com.dingxin.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.dingxin.common.properties.core.DxCoreProperties;

/**  
* Title: DxCorePropertiesConfig 
* Description:  
* @author dicky  
* @date 2018年6月30日 下午9:41:01  
*/
@Configuration
@EnableConfigurationProperties(DxCoreProperties.class)//使得这些配置生效
public class DxCorePropertiesConfig {

}
