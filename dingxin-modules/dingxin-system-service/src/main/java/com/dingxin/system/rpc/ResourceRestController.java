package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.controller.BaseController;
import com.dingxin.system.entity.SysPermission;
import com.dingxin.system.service.permission.SysPermissionService;

/**
 * 
* Title: PermissionRestController 
* Description:  描述: 对外提供权限api
* @author dicky  
* @date 2018年6月28日 下午4:38:38
 */
@RestController
@RequestMapping("/resource")
public class ResourceRestController extends BaseController {

	@Autowired
	private SysPermissionService sysPermissionService;
	
	/**
	 * 根据用户ID查询用户权限
	 * @param userId
	 * @return
	 */
	@GetMapping("/getPermissionsByUserId")
	public List<SysPermission> getPermissionsByUserId(@RequestParam("userId") Long userId) {
		List<SysPermission> permissions = sysPermissionService.findRoles(userId);
		return permissions;
	}
	
	/**
	 * 根据用户身份ID查询用户权限
	 * @param userId
	 * @return
	 */
	@GetMapping("/getPermissionsByIdentId")
	public List<SysPermission> getPermissionsByIdentId(@RequestParam("identId") Long identId) {
		List<SysPermission> permissions = sysPermissionService.findPermissionsByIdentid(identId);
		return permissions;
	}
}