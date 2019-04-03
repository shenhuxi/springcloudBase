package com.dingxin.common.util;

import java.util.HashMap;
import java.util.Map;

import com.dingxin.common.constant.Oauth2Constant;
import com.dingxin.common.properties.core.DxCoreProperties;
import com.dingxin.common.util.JSONUtils;

/**
 * 获取token工具类
* Title: TokenUtil 
* Description:  
* @author dicky  
* @date 2018年6月30日 下午9:05:49
 */
public class TokenUtil {

	private static DxCoreProperties dxCoreProperties = SpringUtil.getBean(DxCoreProperties.class);
    private static String access_token = null;//TODO 不能声明为静态变量，因为token有过期时间，待处理
    
    /**
	 * 请求认证授权服务器获取 access_token
	 * @return
	 * @throws Exception
	 */
	public static String  getAccessToken( ) {
		if(access_token == null || "".equals(access_token)) {
			String codeStr = RestTemplateUtils.doGet(dxCoreProperties.getLogin().getAuthCodeUri());
			if(codeStr!=null && !"".equals(codeStr)) {
				String code = JSONUtils.toJSONObject(codeStr).getJSONObject("data").getString("value").toString();
				Map<String, String> headerMap = new HashMap<>();
				headerMap.put("Content-Type", "application/x-www-form-urlencoded");
				headerMap.put(Oauth2Constant.REQ_HEADER, dxCoreProperties.getLogin().getAuthorization());
				Map<String, String> params = new HashMap<>();
				params.put("grant_type", dxCoreProperties.getLogin().getGrantType());
				params.put("username", dxCoreProperties.getLogin().getUsername());
				params.put("password", dxCoreProperties.getLogin().getPassword());
				params.put("authCode", code);
				String result = RestTemplateUtils.doPost(dxCoreProperties.getLogin().getFormLoginUri(), params, headerMap);
				if (result!= null && result.contains("access_token")) {
					access_token = JSONUtils.toJSONObject(result).getJSONObject("data").getString("access_token").toString();
				}
			}
			
		}
		return access_token;
	}
}
