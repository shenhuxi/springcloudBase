/**
 * 
 */
package com.dingxin.common.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**  
* Title: BCryptPasswordUtil 
* Description:  使用spring security 提供的密码加密工具（不可逆）
* @author dicky  
* @date 2018年6月26日 上午9:57:34  
*/
public class BCryptPasswordUtil {

	private BCryptPasswordUtil() {}
	/**
	 * 比较传入未加密的密码与加密的密码是否一致
	 * @param rawPassword 未加密的密码
	 * @param encodedPassword 加密的密码
	 * @return
	 */
	public static boolean matches(CharSequence rawPassword, String encodedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(rawPassword, encodedPassword);
	}
	
	/**
	 * 密码加密
	 * @param rawPassword
	 * @return
	 */
	public static String encode(CharSequence rawPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(rawPassword);
	}
}
