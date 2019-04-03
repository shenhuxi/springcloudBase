package com.dingxin.oauth2.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.oauth2.userdetails.DxUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Logger LOG = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);
	
	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private ClientDetailsService clientDetailsService;
	
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
    @Autowired
    private ObjectMapper objectMapper;


	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		LOG.info("认证成功!");
		String header = request.getHeader("Authorization");
		if (header == null || (header != null&& !header.startsWith("Basic"))) {
			throw new UnapprovedClientAuthenticationException("请求投中无client信息");
		}
		String[] tokens = this.extractAndDecodeHeader(header, request);
		assert tokens.length == 2;
		// 获取clientId 和 clientSecret
		String clientId = tokens[0];
		String clientSecret = tokens[1];
		// 获取 ClientDetails
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		if (clientDetails == null) {
			throw new UnapprovedClientAuthenticationException("clientId 不存在" + clientId);
			// 判断 方言 是否一致
		} else if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
			throw new UnapprovedClientAuthenticationException("clientSecret 不匹配" + clientId);
		} // 密码授权 模式, 组建 authentication
		Map<String,String> map  = new HashMap<>();
		TokenRequest tokenRequest = new TokenRequest(map, clientId, clientDetails.getScope(),
				"password");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
		DxUserDetails usserDetails = null;
		SysUserVo user = null;
		Object principal = authentication.getPrincipal();
		if(principal instanceof DxUserDetails) {
			user = new SysUserVo();
			usserDetails = (DxUserDetails)principal;
			BeanUtils.copyProperties(usserDetails, user);
			user.setUserName(usserDetails.getUsername());
			user.setPassWord(usserDetails.getPassword());
			try {
				redisTemplate.opsForValue().set(token.getValue() + RedisKeyConstant.USER_KEY, JSON.toJSONString(user), token.getExpiresIn(), TimeUnit.SECONDS);
				redisTemplate.delete(usserDetails.getUsername() + RedisKeyConstant.USER_LOCK_KEY);//删除锁定标识
				redisTemplate.delete(usserDetails.getUsername() + RedisKeyConstant.USER_MISTAKENUMS_KEY);//删除登录错误次数
			} catch (Exception e) {
				LOG.info("error,{}",e.getMessage());
			}
		}
		//设置状态码 
		response.setStatus(HttpStatus.OK.value()); 
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE); 
		response.getWriter().write(objectMapper.writeValueAsString(ResultObject.ok("认证成功！", token)));
	}

	/** * 解码请求头 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {
		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException var7) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}
		String token = new String(decoded, "UTF-8");
		int delim = token.indexOf(":");
		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		} else {
			return new String[] { token.substring(0, delim), token.substring(delim + 1) };
		}
	}

}
