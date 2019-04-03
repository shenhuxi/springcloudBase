package com.dingxin.system.interceptor.argument;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.annotation.CurrentUser;
import com.dingxin.common.constant.Oauth2Constant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.system.entity.SysUser;

/**
 * rest接口封装登陆用户参数
 * @author shixh
 */
@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {
	
	protected final String USER_NULL_MSG = "用户没有登陆,请重新登陆!";
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class);
	}
	
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		try {
			HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
			String authorization = request.getHeader(Oauth2Constant.REQ_HEADER);
			authorization = authorization.replace(Oauth2Constant.TOKEN_SPLIT,"");
			
			String json_User = (String) valueOperations.get(authorization+RedisKeyConstant.USER_KEY);
			SysUser user = JSON.parseObject(json_User,SysUser.class);
			return user;
		} catch (Exception e) {
			throw new RestException(USER_NULL_MSG);
		}
	}

}
