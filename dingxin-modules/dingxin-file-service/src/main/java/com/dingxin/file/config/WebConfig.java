package com.dingxin.file.config;



import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.dingxin.tomcat.properties.TomcatProperties;

@Configuration
@EnableConfigurationProperties(TomcatProperties.class)
public class WebConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private TomcatProperties tomcatProperties;

	/**
	 * 设置tomate的maxSwallowSize
	 * @author shixh
	 * @return
	 */
	@Bean
	public TomcatEmbeddedServletContainerFactory tomcatEmbedded() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
		tomcat.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> {
			if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
				String maxSwallowSize = tomcatProperties.getConnector().getMaxSwallowSize();
				maxSwallowSize = maxSwallowSize.substring(0, maxSwallowSize.length() - 2);
				Integer mb = Integer.parseInt(maxSwallowSize)*1024*1024;
				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(mb);
			}
		});
		return tomcat;
	}
	

 
}
