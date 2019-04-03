package com.dingxin.system.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.system.rpc.IdentRestApi;

/**
 * 描述: feign 调用失败处理
 * 作者: lzb
 * 创建时间: 2018/7/04 11:16
 */
@Service
public class IdentRestApiFallBack implements IdentRestApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());


	/**
	 * 获取当前用户身份信息
	 * @return
	 * @throws Exception 
	 */
	@Override
	public SysUserOrganVo getCurrentIdentInfo() throws Exception {
		logger.error("调用服务异常:getCurrentIdentInfo():{}");
		return null;
	}
	
	/**
	 * 获取用户的所有身份
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysUserOrganVo> getCurrentIdentList() throws Exception {
		logger.error("调用服务异常:getCurrentIdentList()");
		return null;
	}
	
	/**
	 * 根据用户名获取默认身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysUserOrganVo getDefaultIdentByUsername(@RequestParam("username") String username) throws Exception {
		logger.error("调用服务异常:getDefaultIdentByUsername(): {}", username);
		return null;
	}
	
	/**
	 * 根据用户ID获取默认身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public SysUserOrganVo getDefaultIdentByUserId(@RequestParam("userId") Long userId) throws Exception {
		logger.error("调用服务异常:getDefaultIdentByUserId(): {}", userId);
		return null;
	}
	
	/**
	 * 根据用户名获取所有身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysUserOrganVo> geIdentListByUsername(@RequestParam("username") String username) throws Exception {
		logger.error("调用服务异常:geIdentListByUsername(): {}", username);
		return null;
	}
	
	/**
	 * 根据用户ID获取所有身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysUserOrganVo> getIdentListByUserId(@RequestParam("userId") Long userId) throws Exception {
		logger.error("调用服务异常:getIdentListByUserId(): {}", userId);
		return null;
	}

}