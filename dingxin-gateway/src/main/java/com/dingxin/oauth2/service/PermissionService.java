package com.dingxin.oauth2.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import com.dingxin.constants.ApiGateWayConstants;
import com.dingxin.util.StringUtils;

@Service("permissionService")
public class PermissionService {

	private static Logger logger = LoggerFactory.getLogger(PermissionService.class);

	private AntPathMatcher antPathMatcher = new AntPathMatcher();

	/**
	 * 检验当前身份是否有权限访问资源
	 * 
	 * @param request
	 * @param authentication
	 * @return
	 */
	public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
		logger.info("requestURI:{}", request.getRequestURI());
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		boolean hasPermission = true;// 是否拥有权限资源
		// 校验用户是否拥有访问该资源的的权限
		if (!hasPermission && authorities != null && !authorities.isEmpty()) {
			for (GrantedAuthority grantedAuthority : authorities) {
				String authority = grantedAuthority.getAuthority();
				String[] authArray = authority.split(";"); // system/user/list;POST
				if (authArray != null && authArray.length == 2) {
					String url = authArray[0];// 访问路径
					String method = authArray[1];// 访问方法
					if (StringUtils.isNotEmpty(url)
							&& antPathMatcher.match(ApiGateWayConstants.API_PREFIX_URI + url, request.getRequestURI())
							&& request.getMethod().equalsIgnoreCase(method)) {
						hasPermission = true;
						break;
					}
				}
			}
		}
		return hasPermission;
	}
}