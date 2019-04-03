package com.dingxin.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.system.service.userorganrole.SysUserorganRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 系统管理-用户身份角色管理
 * 
 * @author luozb
 *
 */
@RestController
@RequestMapping("/userorganRole")
@Api(tags = "用户身份角色操作", value = "为身份增删查改角色")
public class SysUserorganRoleControl extends BaseController {
	
	@Autowired
	private SysUserorganRoleService sysUserorganRoleService;

	
	/**
	 * 系统管理-身份管理-为身份修改角色（重新分配角色，对应身份角色设置，相当于增删改）
	 * 
	 * @param userorganId,roleIds(如果roleIds为null,则相当于为身份删除所有角色)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/identRolesBind")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-身份管理-身份角色设置")
	@ApiOperation(value = "身份角色设置", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userorganId", value = "身份编号", required = true, paramType = "query"),
			@ApiImplicitParam(name = "roleIds", value = "角色编号列表", paramType = "query") })
	public ResultObject identRolesBind(@RequestParam("userorganId") Long userorganId,@RequestParam(value="roleIds",required=false)String roleIds) {
		if (userorganId == null || userorganId < 0) {
			return ResultObject.fail("缺少参数userorganId");
		}
		sysUserorganRoleService.updateIdentRoles(userorganId, roleIds);
		return ResultObject.ok("身份角色设置成功");
	}

}
