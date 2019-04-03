package com.dingxin.system.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingxin.common.vo.system.SysPermissionVo;
import com.dingxin.system.rpc.SysPermissionServiceFeign;


@Service
public class SysPermissionServiceFeignFallBack implements SysPermissionServiceFeign {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SysPermissionVo> getPermissionsByUserId(Long id) {
		logger.error("调用<根据用户id查用户权限> 异常:{}", id);
		return null;
	}


	@Override
	public List<SysPermissionVo> getPermissionsByIdentId(Long identId) {
		logger.error("调用<根据身份id查用户权限> 异常:{}", identId);
		return null;
	}
}