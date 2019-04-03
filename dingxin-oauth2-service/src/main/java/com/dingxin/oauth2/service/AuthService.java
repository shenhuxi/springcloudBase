/**
 * 
 */
package com.dingxin.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

/**  
* Title: AuthService 
* Description:  
* @author dicky  
* @date 2018年6月26日 上午11:33:14  
*/

@Service
public class AuthService {

	@Autowired
	public TokenStore tokenStore;
	
	/**
	 * 登出
	 * @param authorization
	 * @return
	 */
	public boolean logout(String authorization)  {
		boolean flag = false;
		OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(authorization);
		if(oAuth2AccessToken!=null) {
    		tokenStore.removeAccessToken(oAuth2AccessToken);
    		flag = true;
    	}
		return flag;
	}
}
