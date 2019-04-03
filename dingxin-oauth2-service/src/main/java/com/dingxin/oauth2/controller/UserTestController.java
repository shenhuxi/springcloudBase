/*package com.dingxin.oauth2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.system.rpc.UserRestApi;
import com.dingxin.system.vo.SysUser;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

@RestController
public class UserTestController {
	@Autowired
	UserRestApi userRestApi;*/
	/**
	 * 用户新增
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
/*	@PostMapping("/operateTest")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-用户管理-用户新增")
	@ApiOperation(value = "用户新增", notes = "")
	@ApiImplicitParam(name = "/sysUserTest", value = "文档对象", required = true, paramType = "body", dataType = "SysUser")
	public ResultObject<?> sysUserTest(@RequestBody SysUser sysUser) throws Exception {
		
		if (sysUser.getRoleIds().isEmpty()) {
			return ResultObject.fail("请选择角色!");
		}
		
		SysUser user1 = userRestApi.getCurrentUser();
		if(sysUser.getUserName()!=null) {
			SysUser user2 = userRestApi.getUserByAccount(sysUser.getUserName());
		}
		
		if(sysUser.getId()!=null) {
			SysUser user3 = userRestApi.getUserById(sysUser.getId()); 		
		//SysUser user7 = userRestApi.getUserListByIds(userId); 
		}
		List<SysUser> user4 = userRestApi.getUserList(); 
		List<SysUser> user5 = userRestApi.getUserListByCurrentOrg(); 
		if(sysUser.getOrgId()!=null) {
			List<SysUser> user6 = userRestApi.getUserListByOrgId(sysUser.getOrgId());
			List<SysUser> user9 = userRestApi.getUserListWithRefByOrgId(sysUser.getOrgId()); 
			}
		List<SysUser> user8 = userRestApi.getUserListWithRefByCurrentOrg(); 
		 
		
		return ResultObject.ok("新增成功", sysUser);
	}

	

}
*/