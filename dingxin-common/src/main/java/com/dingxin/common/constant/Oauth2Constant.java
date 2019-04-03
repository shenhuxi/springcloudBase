package com.dingxin.common.constant;

/**
 * 
 * Create By qinzhw
 * 2018年5月3日下午4:17:01
 */
public class Oauth2Constant {
	
    /**
     * token请求头名称
     */
    public static final String REQ_HEADER = "Authorization";

    /**
     * token分割符
     */
    public static final String TOKEN_SPLIT = "bearer ";
    
    
    public static void main(String [] args) {
    	String a= "bearer bc938bc7-d6e1-43ac-a2fb-6d559d57ff9d";
    	System.out.println(a.replace("bearer ", ""));
    }
}
