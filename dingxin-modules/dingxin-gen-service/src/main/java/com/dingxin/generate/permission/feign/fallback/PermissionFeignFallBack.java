package com.dingxin.generate.permission.feign.fallback;

import com.dingxin.common.util.ResultObject;
import com.dingxin.generate.permission.feign.PermissionFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 描述: feign 调用失败处理
 * 作者: qinzhw
 * 创建时间: 2018/6/14 11:16
 */
@Service
public class PermissionFeignFallBack implements PermissionFeign {

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	@Override
	public ResultObject permissionGen(String list, String appName) {
		logger.error("调用<生成当前模块权限脚本> 异常:{}", list);
		return ResultObject.fail("生成当前模块权限脚本");
	}
}