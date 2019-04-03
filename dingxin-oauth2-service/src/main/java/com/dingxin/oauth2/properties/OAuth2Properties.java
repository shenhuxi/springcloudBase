package com.dingxin.oauth2.properties;

/**
 * 
* Title: OAuth2Properties 
* Description:  
* @author dicky  
* @date 2018年6月25日 下午5:53:33
 */
public class OAuth2Properties {

    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "dingxin";

    /**
     * 客户端配置
     */
    private OAuth2ClientProperties[] clients = {};

	public String getJwtSigningKey() {
		return jwtSigningKey;
	}

	public void setJwtSigningKey(String jwtSigningKey) {
		this.jwtSigningKey = jwtSigningKey;
	}

	public OAuth2ClientProperties[] getClients() {
		return clients;
	}

	public void setClients(OAuth2ClientProperties[] clients) {
		this.clients = clients;
	}

}
