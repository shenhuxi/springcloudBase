package com.dingxin.oauth2.handler;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.util.ResultObject;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
* Title: MyAuthenticationFailHandler 
* Description:  自定义登录失败处理器
* @author dicky  
* @date 2018年6月19日 下午4:50:14
 */
@Component("myAuthenticationFailHandler") 
public class MyAuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler { 
	private static Logger LOG = LoggerFactory.getLogger(MyAuthenticationFailHandler.class);
	
	@Autowired
    private ObjectMapper objectMapper;
	
	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException { 
		LOG.error("认证失败，{}",exception.getMessage());
		String auth_type = request.getParameter("auth_type"); // 认证类型 的用户参数名，默认是username 
		String username = request.getParameter(StringUtils.isEmpty(auth_type) ? "username" : auth_type);
		String errorMsg = "";
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		try {
			String nums = (String) valueOperations.get(username + RedisKeyConstant.USER_MISTAKENUMS_KEY);
			if (StringUtils.isEmpty(nums)) {
				valueOperations.set(username + RedisKeyConstant.USER_MISTAKENUMS_KEY, "1",
						RedisKeyConstant.TIME_MINUTES_LOCK, TimeUnit.SECONDS);
			} else {
				if (Integer.parseInt(nums) >= 5) {
					valueOperations.set(username + RedisKeyConstant.USER_LOCK_KEY, "true",
							RedisKeyConstant.TIME_MINUTES_LOCK, TimeUnit.SECONDS);
					redisTemplate.delete(username + RedisKeyConstant.USER_MISTAKENUMS_KEY);
					errorMsg = "密码输入错误,已超过6次,将被锁定!";
				} else {
					valueOperations.set(username + RedisKeyConstant.USER_MISTAKENUMS_KEY, (Integer.parseInt(nums) + 1) + "");
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		if(StringUtils.isEmpty(errorMsg)) {
			errorMsg = exception.getMessage();
		}
		//设置状态码 
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()); 
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE); 
		//将 登录失败 信息打包成json格式返回 
		response.getWriter()
			.write(objectMapper
			.writeValueAsString(ResultObject.fail("认证失败",errorMsg ))); 
	}
}
