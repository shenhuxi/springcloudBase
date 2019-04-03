package com.dingxin.oauth2.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dingxin.common.properties.url.DxUrlsProperties;


/**
 * 
* Title: SecurityProperties 
* Description:  
* @author dicky  
* @date 2018年6月25日 下午5:43:41
 */
@ConfigurationProperties(prefix = "dingxin.security")  //意思是这个类会读取整个系统中以dingxin.security开头的配置
public class DxSecurityProperties {

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();
    
    private DxUrlsProperties urls = new DxUrlsProperties();

	public OAuth2Properties getOauth2() {
		return oauth2;
	}

	public void setOauth2(OAuth2Properties oauth2) {
		this.oauth2 = oauth2;
	}

	public DxUrlsProperties getUrls() {
		return urls;
	}

	public void setUrls(DxUrlsProperties urls) {
		this.urls = urls;
	}


}
