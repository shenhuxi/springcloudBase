package com.dingxin.system.feign.fallback;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingxin.system.rpc.ResourceRestApi;
import com.dingxin.system.vo.SysPermission;

/**
 * 描述: feign 调用失败处理
 * 作者: lzb
 * 创建时间: 2018/7/04 11:16
 */
@Service
public class ResourceRestApiFallBack implements ResourceRestApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<SysPermission> getPermissionsByUserId(Long userId) {
		logger.error("调用服务异常:getPermissionsByUserId(): {}", userId);
		return null;
	}

	@Override
	public List<SysPermission> getPermissionsByIdentId(Long identId) {
		logger.error("调用服务异常:getPermissionsByIdentId(): {}", identId);
		return null;
	}
	
}