package com.dingxin.oauth2.config;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import com.dingxin.oauth2.properties.DxSecurityProperties;
import com.dingxin.oauth2.properties.OAuth2ClientProperties;
import com.dingxin.oauth2.service.MyUserDetailsService;

/**
 * 
 * Title: DxAuthorizationServerConfig Description:
 * 
 * @author dicky
 * @date 2018年6月22日 下午10:56:32
 */

@Configuration
@EnableAuthorizationServer
public class DxAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private DxSecurityProperties dxSecurityProperties;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private MyUserDetailsService myUserDetailsService;

	@Primary
	@Bean
	public AuthorizationServerTokenServices authorizationServerTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore);
		defaultTokenServices.setSupportRefreshToken(true);
		defaultTokenServices.setReuseRefreshToken(false);
		return defaultTokenServices;
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(authenticationManager)
				.userDetailsService(myUserDetailsService)// 若无，refresh_token会有UserDetailsService
				.tokenServices(authorizationServerTokenServices());
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.allowFormAuthenticationForClients()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		OAuth2ClientProperties[] clientProperties = dxSecurityProperties.getOauth2().getClients();
		InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
		if (ArrayUtils.isNotEmpty(clientProperties)) {
			// 如果配了的话,在里面遍历出来
			for (OAuth2ClientProperties config : clientProperties) {
				// 在循环里设置
				builder.withClient(config.getClientId())
						// 指定id的密码
						.secret(config.getClientSecret())
						// 发出去的令牌的有效时间
						.accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
						// 针对tihom的这些应用所能支持的授权模式是哪些,一共五种方式
						.authorizedGrantTypes(config.getGrantTypes())
						// 关于refreshToken的失效时间
						.refreshTokenValiditySeconds(config.getRefreshTokenValiditySeconds())
						// OAuth中的权限,这里配置后请求中就不会带上scope参数
						.scopes(config.getScopes());
			}
		}
	}
}
