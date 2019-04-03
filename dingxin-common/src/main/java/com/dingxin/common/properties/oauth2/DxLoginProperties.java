package com.dingxin.common.properties.oauth2;

/**
 * 
* Title: DxLoginProperties 
* Description:  登录信息配置（可用于单元测试获取token）
* @author dicky  
* @date 2018年6月26日 下午2:30:58
 */

public class DxLoginProperties {
	
	private String authCodeUri;//验证码地址
	
	private String formLoginUri;//登录地址
	
	private String authorization; //认证授权加密信息
	
	private String grantType; //授权类型
	
	private String username; //用户名
	
	private String password;//密码

	public String getAuthCodeUri() {
		return authCodeUri;
	}

	public void setAuthCodeUri(String authCodeUri) {
		this.authCodeUri = authCodeUri;
	}

	public String getFormLoginUri() {
		return formLoginUri;
	}

	public void setFormLoginUri(String formLoginUri) {
		this.formLoginUri = formLoginUri;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}

  
