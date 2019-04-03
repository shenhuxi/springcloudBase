package com.dingxin.tomcat.properties;


import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * TomcatProperties
 * @author shixh
 */
@ConfigurationProperties(prefix = TomcatProperties.PREFIX)
public class TomcatProperties {
	
	public static final String PREFIX = "tomcat";
	@Valid
	private Connector connector = new Connector();

	public Connector getConnector() {
		return connector;
	}

	public void setConnector(Connector connector) {
		this.connector = connector;
	}

	public static class Connector {
		@Pattern(regexp = "^(\\d+)(MB)$", message = "must start with number and end with MB")
		private String maxSwallowSize;
		public String getMaxSwallowSize() {
			return maxSwallowSize;
		}
		public void setMaxSwallowSize(String maxSwallowSize) {
			this.maxSwallowSize = maxSwallowSize;
		}
	}

}
