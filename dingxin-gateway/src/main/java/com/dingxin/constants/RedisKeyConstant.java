package com.dingxin.constants;

/**
 * redis 公共KEY
 * @author shixh
 */
public class RedisKeyConstant {
	
	public static final String USER_KEY = "_user";//用户信息
	public static final String USER_ORG_KEY ="user_org";//身份信息
	public static final int TIME_MINUTES_LOCK = 10*60;//锁定时间
	public static final int TIME_MINUTES_AuthCode = 10*60;//锁定时间
	public static final String USER_LOCK_KEY = "_lock";//用户锁定
	public static final String USER_MISTAKENUMS_KEY = "_mistakeNums";//输入密码错误次数
	public static final String USER_AUTHCODE_KEY = "_authCode";//输入密码错误次数
	
}
