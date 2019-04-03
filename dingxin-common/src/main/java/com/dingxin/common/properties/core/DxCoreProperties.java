/**
 * 
 */
package com.dingxin.common.properties.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dingxin.common.properties.oauth2.DxLoginProperties;
import com.dingxin.common.properties.system.DxSystemProperties;

/**  
* Title: DxCoreProperties 
* Description:  核心配置类
* @author dicky  
* @date 2018年6月30日 下午9:43:51  
*/
@ConfigurationProperties(prefix = "dingxin.core")  //意思是这个类会读取整个系统中以dingxin.core开头的配置
public class DxCoreProperties {

	private DxLoginProperties login = new DxLoginProperties();//单元测试登录获取token配置

	private DxSystemProperties system = new DxSystemProperties();//系统权限资源生成相关配置
	
	public DxLoginProperties getLogin() {
		return login;
	}

	public void setLogin(DxLoginProperties login) {
		this.login = login;
	}

	public DxSystemProperties getSystem() {
		return system;
	}

	public void setSystem(DxSystemProperties system) {
		this.system = system;
	}
	
	
}
