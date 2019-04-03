package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.IdentRestApiFallBack;

/**  
* @ClassName: IdentRestApi  
* @Description: 身份对外接口
* @author luozb  
* @date 2018年7月6日 下午6:18:35  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
fallback = IdentRestApiFallBack.class,
configuration = FeignConfig.class)
public interface IdentRestApi {
	/**
	 * 获取当前用户身份信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/ident/getCurrentIdentInfo", method = RequestMethod.GET)
	public SysUserOrganVo getCurrentIdentInfo() throws Exception;
	
	/**
	 * 获取用户的所有身份
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ident/getCurrentIdentList", method = RequestMethod.GET)
	public List<SysUserOrganVo> getCurrentIdentList() throws Exception;
	
	/**
	 * 根据用户名获取默认身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ident/getCurrentIdentList", method = RequestMethod.GET)
	public SysUserOrganVo getDefaultIdentByUsername(@RequestParam("username") String username) throws Exception;
	
	/**
	 * 根据用户ID获取默认身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ident/getCurrentIdentList", method = RequestMethod.GET)
	public SysUserOrganVo getDefaultIdentByUserId(@RequestParam("userId") Long userId) throws Exception;
	
	/**
	 * 根据用户名获取所有身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ident/getCurrentIdentList", method = RequestMethod.GET)
	public List<SysUserOrganVo> geIdentListByUsername(@RequestParam("username") String username) throws Exception;
	
	/**
	 * 根据用户ID获取所有身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/ident/getCurrentIdentList", method = RequestMethod.GET)
	public List<SysUserOrganVo> getIdentListByUserId(@RequestParam("userId") Long userId) throws Exception;
	
}