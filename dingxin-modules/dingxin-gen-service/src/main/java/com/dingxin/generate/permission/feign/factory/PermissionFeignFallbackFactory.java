package com.dingxin.generate.permission.feign.factory;

import com.dingxin.common.util.ResultObject;
import com.dingxin.generate.permission.feign.PermissionFeign;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class PermissionFeignFallbackFactory implements FallbackFactory<PermissionFeign> {
  
    @Override  
    public PermissionFeign create(Throwable e) {
        System.out.println("错误信息："+e.getMessage());
        return new PermissionFeign() {
            @Override
            public ResultObject permissionGen(String list ,String appName) {
                return ResultObject.fail(e.getMessage());
            }
        };
    }  
  
}  