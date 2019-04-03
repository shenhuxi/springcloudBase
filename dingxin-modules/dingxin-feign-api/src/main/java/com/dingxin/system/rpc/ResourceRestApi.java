package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.ResourceRestApiFallBack;
import com.dingxin.system.vo.SysPermission;

/**  
* @ClassName: ResourceRestApi  
* @Description: 资源对外接口
* @author luozb  
* @date 2018年7月9日 下午6:19:12  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
fallback = ResourceRestApiFallBack.class,
configuration = FeignConfig.class)
public interface ResourceRestApi {

	/**
	 * 根据用户ID查询用户权限
	 * @param userId
	 * @return
	 */
	@GetMapping("/resource/getPermissionsByUserId")
	public List<SysPermission> getPermissionsByUserId(@RequestParam("userId") Long userId);
	
	/**
	 * 根据用户身份ID查询用户权限
	 * @param userId
	 * @return
	 */
	@GetMapping("/resource/getPermissionsByIdentId")
	public List<SysPermission> getPermissionsByIdentId(@RequestParam("identId") Long identId);
}