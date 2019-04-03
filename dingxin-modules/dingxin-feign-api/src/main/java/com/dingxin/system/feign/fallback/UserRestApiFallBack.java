package com.dingxin.system.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingxin.system.rpc.UserRestApi;
import com.dingxin.system.vo.SysUser;

/**
 * 描述: feign 调用失败处理 
 * 作者: lzb 
 * 创建时间: 2018/7/04 11:16
 */
@Service
public class UserRestApiFallBack implements UserRestApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Override
	public SysUser getCurrentUser() throws Exception {
		logger.error("调用服务异常:getCurrentUser()");
		return null;
	}

	@Override
	public SysUser getUserByAccount(String account) {
		logger.error("调用服务异常:getUserByAccount(): {}", account);
		return null;
	}

	@Override
	public SysUser getUserById(Long userId) throws IllegalAccessException {
		logger.error("调用服务异常:getUserById(): {}", userId);
		return null;
	}

	@Override
	public List<SysUser> getUserList() {
		logger.error("调用服务异常:getUserList()");
		return null;
	}

	@Override
	public List<SysUser> getUserListByCurrentOrg() throws Exception {
		logger.error("调用服务异常:getUserListByCurrentOrg()");
		return null;
	}

	@Override
	public List<SysUser> getUserListByIds(List<Long> userId) {
		logger.error("调用服务异常:getUserListByIds(): {}", userId);
		return null;
	}

	@Override
	public List<SysUser> getUserListByOrgId(Long orgId) throws IllegalAccessException {
		logger.error("调用服务异常:getUserListByOrgId(): {}", orgId);
		return null;
	}

	@Override
	public List<SysUser> getUserListWithRefByCurrentOrg() throws Exception {
		logger.error("调用服务异常:getUserListWithRefByCurrentOrg()");
		return null;
	}

	@Override
	public List<SysUser> getUserListWithRefByOrgId(Long orgId) throws IllegalAccessException {
		logger.error("调用服务异常:getUserListWithRefByOrgId(): {}", orgId);
		return null;
	}
}