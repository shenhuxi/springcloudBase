package com.dingxin.system.rpc;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.SysUserServiceFeignFallBack;

/**  
* @ClassName: SysUserServiceFeign  
* @Description: 用于登录  
* @author luozb  
* @date 2018年7月5日 下午6:21:05  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
		fallback = SysUserServiceFeignFallBack.class,
		configuration = FeignConfig.class)
public interface SysUserServiceFeign {

	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
    @RequestMapping(value = "/user/findByUserName/{username}", method = RequestMethod.GET)
    public String findByUserName(@PathVariable("username") String username);

	/**
	 * 根据手机号码查询用户
	 * @param mobile
	 * @return
	 */
    @RequestMapping(value = "/user/findByMobile/{mobile}", method = RequestMethod.GET)
	public String findByMobile(@PathVariable("mobile") String mobile);


}
