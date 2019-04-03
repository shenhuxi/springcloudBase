package com.dingxin.oauth2.properties;

/**
 * 
* Title: OAuth2ClientProperties 
* Description:  认证服务器注册的第三方应用配置项
* @author dicky  
* @date 2018年6月25日 下午5:44:44
 */
public class OAuth2ClientProperties {
    /**
     * 第三方应用appId
     */
    private String clientId;

    /**
     * 第三方应用appSecret
     */
    private String clientSecret;

    /**
     * 针对此应用发出的token的有效时间，默认为12小时
     */
    private int accessTokenValiditySeconds = 60*60*12;
    /**
     * 刷新token的有限时间
     */
    private int refreshTokenValiditySeconds = 60*60*24*30;
    
    private String[] scopes;

	private String[] grantTypes;//授权类型
	
	
    //具体使用时可以在这里继续加上想要配置的属性

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

	public String[] getScopes() {
		return scopes;
	}

	public void setScopes(String[] scopes) {
		this.scopes = scopes;
	}

	public int getRefreshTokenValiditySeconds() {
		return refreshTokenValiditySeconds;
	}

	public void setRefreshTokenValiditySeconds(int refreshTokenValiditySeconds) {
		this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
	}

	public String[] getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(String[] grantTypes) {
		this.grantTypes = grantTypes;
	}

}
