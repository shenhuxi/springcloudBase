package com.dingxin.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
* Title: DxApiGateWateProperties 
* Description:  
* @author dicky  
* @date 2018年6月25日 下午5:43:41
 */
@ConfigurationProperties(prefix = "dingxin.gateway")  //意思是这个类会读取整个系统中以dingxin.security开头的配置
public class DxApiGateWateProperties {

	private DxUrlsProperties urls = new DxUrlsProperties();//URL配置
	
	private ValidateCodeProperties code = new ValidateCodeProperties();//验证码配置

	public DxUrlsProperties getUrls() {
		return urls;
	}

	public void setUrls(DxUrlsProperties urls) {
		this.urls = urls;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}


}
