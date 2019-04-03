/**
 * 
 */
package com.dingxin.oauth2;

/**
 * 
* Title: SecurityConstants 
* Description:  常量
* @author dicky  
* @date 2018年6月22日 下午11:00:47
 */
 
public interface SecurityConstants {
	
	/**
	 * 默认的处理验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";

	/**
	 * 默认登录认证请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL = "/login";
	
	/**
	 * 切换身份请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_IDENT = "/login/ident";
	/**
	 * 默认的手机验证码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_MOBILE = "/login/mobile";

	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "imageCode";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "smsCode";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_MOBILE = "mobile";
	
	public static final String DEFAULT_PARAMETER_NAME_USERNAME = "username";
	
	public static final String DEFAULT_PARAMETER_NAME_IDENTID = "identid";
	/**
	 * 请求头参数名
	 */
	public static final String HEADER_PARAMETER_TOKEN = "Token";
	
	public static final String USER_NOT_EXIST = " 用户不存在！";
	
	public static final String USER_DELETED_STATE = " 用用户已被删除! ";
	
	public static final String USER_STOPPED_STATE = " 用户还未启用!";
	
	public static final String USER_DESTROYED_STATE = " 用户已被注销!";
	
	public static final String USER_LOCKED_STATE = " 错误登陆过多,用户已被锁定,十分钟后再次尝试!";
	
	public static final String IDENTITY_SWITCH_FAILURE = " 用户未登录，切换用户身份失败!";

	
}
