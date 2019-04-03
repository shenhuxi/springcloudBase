package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.system.config.FeignConfig;
import com.dingxin.common.vo.system.SysPermissionVo;
import com.dingxin.system.feign.fallback.SysPermissionServiceFeignFallBack;

/**  
* @ClassName: SysPermissionServiceFeign  
* @Description: 权限对外接口  
* @author luozb  
* @date 2018年7月6日 下午6:19:29  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
        fallback = SysPermissionServiceFeignFallBack.class,
        configuration = FeignConfig.class)
public interface SysPermissionServiceFeign {

    /**
     * 描述: 根据用户id查用户权限
     * 作者: qinzhw
     * 创建时间: 2018/6/14 16:57
     */
    @RequestMapping(value = "/resource/getPermissionsByUserId", method = RequestMethod.GET)
    public List<SysPermissionVo> getPermissionsByUserId(@RequestParam("userId") Long userId);

	/**
	 * @param 根据身份ID查询权限
	 * @return
	 */
    @RequestMapping(value = "/resource/getPermissionsByIdentId", method = RequestMethod.GET)
	List<SysPermissionVo> getPermissionsByIdentId(@RequestParam("identId")Long identId);


}
