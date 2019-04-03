package com.dingxin.generate.permission.feign;

import com.dingxin.common.util.ResultObject;
import com.dingxin.generate.permission.config.FeignConfig;
import com.dingxin.generate.permission.feign.fallback.PermissionFeignFallBack;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 描述: 
 * 作者: qinzhw
 * 创建时间: 2018/6/15 17:38
 */
@FeignClient(name = "dingxin-system-service/system",
        fallback = PermissionFeignFallBack.class,
        configuration = FeignConfig.class)
public interface PermissionFeign {

    /**
     * 描述: 生成当前模块权限脚本
     * 作者: qinzhw
     * 创建时间: 2018/6/15 17:38
     */
    @RequestMapping(value = "/permission/gen", method = RequestMethod.POST)
    public ResultObject permissionGen(@RequestParam("perjson") String perjson, @RequestParam("appName") String appName);
}