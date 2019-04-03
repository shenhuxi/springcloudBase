/**
 * 
 */
package com.dingxin.common.entity.security;

import java.io.Serializable;

/**  
* Title: AccessToken 
* Description:  认证token信息
* @author dicky  
* @date 2018年6月26日 上午11:07:26  
*/
public class AccessToken implements Serializable{

	private static final long serialVersionUID = 9123943654312133945L;
	
	private String access_token;
	
	private String refresh_token;//刷新token
	
	private String token_type;//类型
	
	private Integer expires_in;//token剩余有效时间（毫秒）
	
	private String scope;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getToken_type() {
		return token_type;
	}
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	
}
