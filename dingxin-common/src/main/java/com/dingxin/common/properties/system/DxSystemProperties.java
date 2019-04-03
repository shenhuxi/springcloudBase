/**
 * 
 */
package com.dingxin.common.properties.system;

/**  
* Title: DxSystemProperties 
* Description:  
* @author dicky  
* @date 2018年7月3日 下午5:10:31  
*/
public class DxSystemProperties {
	
	private boolean permissionGen = false;//是否生成权限资源
	
	private String permissionGenUri ;//生产权限资源的访问地址
	
	private String contextPath;//当前应用上下文路径

	public boolean isPermissionGen() {
		return permissionGen;
	}

	public void setPermissionGen(boolean permissionGen) {
		this.permissionGen = permissionGen;
	}

	public String getPermissionGenUri() {
		return permissionGenUri;
	}

	public void setPermissionGenUri(String permissionGenUri) {
		this.permissionGenUri = permissionGenUri;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	
	
	
}
