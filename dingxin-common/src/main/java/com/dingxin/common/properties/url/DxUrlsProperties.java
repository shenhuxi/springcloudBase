package com.dingxin.common.properties.url;

import java.util.ArrayList;
import java.util.List;


/**
 * 
* Title: DxUrlsProperties 
* Description:  免认证的URL配置
* @author dicky  
* @date 2018年6月26日 下午2:30:58
 */

public class DxUrlsProperties {
    private List<String> noAuth = new ArrayList<>();

	public List<String> getNoAuth() {
		return noAuth;
	}

	public void setNoAuth(List<String> noAuth) {
		this.noAuth = noAuth;
	}
	
}

  
