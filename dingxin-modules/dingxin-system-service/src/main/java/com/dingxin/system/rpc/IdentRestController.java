package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.controller.BaseController;
import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.service.user.SysUserService;
import com.dingxin.system.service.userorgan.SysUserOrganService;

/**
 * 
* Title: IdentRestController 
* Description:  对外提供身份信息api
* @author dicky  
* @date 2018年6月28日 下午5:09:03
 */
@RestController
@RequestMapping("/ident")
public class IdentRestController extends BaseController {
	
	@Autowired
	private SysUserOrganService  sysUserOrganService;
	
	@Autowired
	private SysUserService sysUserService;
	/**
	 * 获取当前用户身份信息
	 * @return
	 * @throws Exception 
	 */
	@GetMapping("/getCurrentIdentInfo")
	public SysUserOrganVo getCurrentIdentInfo() throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		SysUserOrganVo vo = sysUserOrganService.findUserOrganVoByIdentId(loginUser.getIdentId(),loginUser.getOrgId());
		return vo;
	}
	
	/**
	 * 获取用户的所有身份
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getCurrentIdentList")
	public List<SysUserOrganVo> getCurrentIdentList() throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		List<SysUserOrganVo> list = sysUserOrganService.findSysUserOrganVoListByUserId(loginUser.getId(), loginUser.getOrgId());
		return list;
	}
	
	/**
	 * 根据用户名获取默认身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getDefaultIdentByUsername")
	public SysUserOrganVo getDefaultIdentByUsername(@RequestParam("username") String username) throws Exception {
		SysUser user = sysUserService.findByUserName(username);
		SysUserOrganVo vo = sysUserOrganService.findUserOrganVoByUserId(user.getId(), user.getOrgId());
		return vo;
	}
	
	/**
	 * 根据用户ID获取默认身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getDefaultIdentByUserId")
	public SysUserOrganVo getDefaultIdentByUserId(@RequestParam("userId") Long userId) throws Exception {
		SysUser user = sysUserService.findOne(userId);
		SysUserOrganVo vo = sysUserOrganService.findUserOrganVoByUserId(user.getId(), user.getOrgId());
		return vo;
	}
	
	/**
	 * 根据用户名获取所有身份信息
	 * @param username
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/geIdentListByUsername")
	public List<SysUserOrganVo> geIdentListByUsername(@RequestParam("username") String username) throws Exception {
		SysUser user = sysUserService.findByUserName(username);
		List<SysUserOrganVo> list = sysUserOrganService.findSysUserOrganVoListByUserId(user.getId(), user.getOrgId());
		return list;
	}
	
	/**
	 * 根据用户ID获取所有身份信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getIdentListByUserId")
	public List<SysUserOrganVo> getIdentListByUserId(@RequestParam("userId") Long userId) throws Exception {
		SysUser user = sysUserService.findOne(userId);
		List<SysUserOrganVo> list = sysUserOrganService.findSysUserOrganVoListByUserId(user.getId(), user.getOrgId());
		return list;
	}
}