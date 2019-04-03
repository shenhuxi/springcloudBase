/**
 * 
 */
package com.dingxin.oauth2.authentication.identity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.dingxin.oauth2.SecurityConstants;



/**
 * 
* Title: IdentAuthenticationFilter 
* Description:  
* @author dicky  
* @date 2018年6月19日 下午5:49:52
 */

public class IdentAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~ Static fields/initializers
	// =====================================================================================

	private String usernameParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_USERNAME;
	private String identidParameter = SecurityConstants.DEFAULT_PARAMETER_NAME_IDENTID;
	private boolean postOnly = true;

	// ~ Constructors
	// ===================================================================================================

	public IdentAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_IDENT, "POST"));
	}

	// ~ Methods
	// ========================================================================================================

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		String username = obtainUsername(request);//用户名
		String identid = obtainIdentid(request);//身份ID
		String token = obtainToken(request);
		if (username == null || "".equals(username)) {
			throw new AuthenticationServiceException("用户名不能为空！" );
		}
		if (identid == null || "".equals(identid)) {
			throw new AuthenticationServiceException("身份ID不能为空！" );
		}
		if (token == null || "".equals(token)) {
			throw new AuthenticationServiceException("token不能为空！" );
		}
		username = username.trim();
		identid = identid.trim();
		token = token.trim();
		IdentAuthenticationToken authRequest = new IdentAuthenticationToken(username,identid,token);
		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		return this.getAuthenticationManager().authenticate(authRequest);
	}


	/**
	 * 获取用户名
	 */
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(usernameParameter);
	}

	/**
	 * 获取身份ID
	 * @param request
	 * @return
	 */
	protected String obtainIdentid(HttpServletRequest request) {
		return request.getParameter(identidParameter);
	}

	protected String obtainToken(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_PARAMETER_TOKEN);
		return token;
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, IdentAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * Sets the parameter name which will be used to obtain the username from
	 * the login request.
	 *
	 * @param usernameParameter
	 *            the parameter name. Defaults to "username".
	 */
	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setIdentidParameter(String identidParameter) {
		Assert.hasText(identidParameter, "Identid parameter must not be empty or null");
		this.identidParameter = identidParameter;
	}

	/**
	 * Defines whether only HTTP POST requests will be allowed by this filter.
	 * If set to true, and an authentication request is received which is not a
	 * POST request, an exception will be raised immediately and authentication
	 * will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method
	 * will be called as if handling a failed authentication.
	 * <p>
	 * Defaults to <tt>true</tt> but may be overridden by subclasses.
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return usernameParameter;
	}

	public final String getIdentidParameter() {
		return identidParameter;
	}

}
