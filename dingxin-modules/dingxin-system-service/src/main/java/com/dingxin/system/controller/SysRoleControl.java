package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.system.entity.SysPermission;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.service.permissionrole.SysRolePermissionService;
import com.dingxin.system.service.role.SysRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统管理-角色管理
 * 
 * @author shixh
 *
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色操作", description = "增删查改与分配权限")
public class SysRoleControl extends BaseController {

	@Autowired
	private SysRoleService sysRoleService;
	
	@Autowired
	private SysRolePermissionService sysRolePermissionService;

	/**
	 * 系统管理-角色管理-新增角色
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/operate")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-角色管理-新增角色")
	@ApiOperation(value = "角色新增", notes = "")
	@ApiImplicitParam(name = "entity", value = "角色", required = true, paramType = "body")
	public ResultObject save(@ApiParam @RequestBody @Valid SysRole entity) throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		entity.setCreateUserId(loginUser.getId());
		entity.setCreateUserName(loginUser.getUserName());
		SysRole sysRole = sysRoleService.checkAndSave(entity);
		return ResultObject.ok("新增成功", sysRole);
	}

	/**
	 * 系统管理-角色管理-角色修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/operate")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-角色管理-角色修改")
	@ApiOperation(value = "角色修改", notes = "")
	@ApiImplicitParam(name = "entity", value = "角色", required = true, paramType = "body", dataType = "string")
	public ResultObject update(@ApiParam @RequestBody @Valid SysRole entity) throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		entity.setModifyUserId(loginUser.getId());
		entity.setModifyUserName(loginUser.getUserName());
		SysRole sysRole = sysRoleService.checkAndUpdate(entity);
		return ResultObject.ok("修改成功!", sysRole);
	}

	/**
	 * 系统管理-角色管理-删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-角色管理-删除")
	@ApiOperation(value = "根据角色ID删除角色", notes = "")
	@ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject delete(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择角色数据.");
		}
		sysRoleService.deleteById(id);
		return ResultObject.ok("删除成功!", id);
	}
	
	/**
	 * 根据角色ID获取角色及所拥有的权限
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findPerByRoleId/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-角色管理-查询权限")
	@ApiOperation(value = "根据角色ID查询权限", notes = "")
	@ApiImplicitParam(name = "id", value = "角色ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject findPerByRoleId(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择角色ID.");
		}
		SysRole sysRole = sysRoleService.findOne(id);
		if (sysRole != null) {
			List<SysPermission> pers = sysRolePermissionService.findPerByRoleId(id);
			if (!CollectionUtils.isEmpty(pers)) {
				return ResultObject.ok(pers);
			}
			return ResultObject.fail("该角色尚未分配权限!");
		}
		return ResultObject.fail("查无此角色!");
	}

	
	/**
	 * 系统管理-角色管理-为角色修改权限（重新分配权限，对应权限设置，相当于增删改）
	 * 
	 * @param roleId  sysPermissionIds(如果权限ids为null,则相当于为角色删除所有权限)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/rolePerBind")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-权限管理-权限设置")
	@ApiOperation(value = "权限设置", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleId", value = "角色编号", required = true, paramType = "body"),
			@ApiImplicitParam(name = "sysPermissionIds", value = "权限编号列表", paramType = "body") })
	public ResultObject rolePerBind(@RequestParam("roleId") Long roleId,@RequestParam(value="sysPermissionIds",required=false)String sysPermissionIds) throws Exception {
		if (roleId == null || roleId < 0) {
			return ResultObject.fail("缺少参数roleId");
		}
		sysRolePermissionService.updateRolePer(roleId, sysPermissionIds);
		return ResultObject.ok("权限设置成功");
	}

	/**
	 * 系统管理-角色管理-查询
	 * 
	 * @return 可根据id查询
	 */
	@PostMapping(value = "/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-角色管理-查询")
	@ApiOperation(value = "查询角色列表(带分页)", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject list() throws ParseException {
		Map<String, Object> paramsMap = getParameterMap();
		// searchParams.put("EQ_userName","show");//自定义添加条件
		PageRequest pageRequest = buildPageRequest(paramsMap);
		Page<SysRole> sysUsers = sysRoleService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(sysUsers);
	}
	
	
	
	/**
	 * 系统管理-角色管理-查询
	 * 
	 * @return 不带分页但按sort字段降序排列返回查询到的所有角色
	 */
	@PostMapping(value = "/listNoPage")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-角色管理-查询（不分页）")
	@ApiOperation(value = "查询角色列表(按sort字段降序,不带分页)", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form") })
	public ResultObject listNoPage() throws ParseException {
		Map<String, Object> paramsMap = getParameterMap();
		// searchParams.put("EQ_userName","show");//自定义添加条件
		String st = (String) paramsMap.get("sort");
		if (st != null) {
			paramsMap.put(CommonConstant.PAGE_SORTTYPE, Direction.DESC);
			paramsMap.put(CommonConstant.PAGE_SORT, st);
		}
		
		List<SysRole> sysroles = sysRoleService.findAll(paramsMap);
		return ResultObject.ok(sysroles);
	}
	/**
	 * 根据身份ID获取角色
	 * 
	 * @param identid
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/getIdentityRole/{identid}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-角色管理-获取身份关联的角色")
	@ApiOperation(value = "根据身份ID获取角色", notes = "")
	@ApiImplicitParam(name = "identid", value = "身份ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject<?> getIdentityRole(@PathVariable("identid") Long identid) throws Exception {
		if (identid == null ) {
			return ResultObject.fail("请选择用户ID.");
		}
		List<SysRole> sysroles = sysRoleService.findRolesByIndentId(identid);
		return ResultObject.ok(sysroles);
	
	}
}
