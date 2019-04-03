package com.dingxin.system.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingxin.system.rpc.SysUserServiceFeign;

@Service
public class SysUserServiceFeignFallBack implements SysUserServiceFeign {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String findByUserName(String username) {
		logger.error("调用服务异常:username {}", username);
		return null;
	}

	@Override
	public String findByMobile(String mobile) {
		logger.error("调用服务异常:mobile {}", mobile);
		return null;
	}


}