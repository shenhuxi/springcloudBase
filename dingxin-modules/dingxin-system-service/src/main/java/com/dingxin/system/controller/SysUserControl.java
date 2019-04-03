package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.system.entity.SysOrganRole;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.service.organrole.SysOrganRoleService;
import com.dingxin.system.service.role.SysRoleService;
import com.dingxin.system.service.user.SysUserService;
import com.dingxin.system.service.userorgan.SysUserOrganService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统管理-用户管理
 * 
 * @author shixh
 *
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户操作", description = "增删查改启停注销")
public class SysUserControl extends BaseController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserOrganService sysUserOrganService;
	
	@Autowired
	private  SysRoleService roleService;

	@Autowired
	private  SysOrganRoleService organRoleService;
	
	/**
	 * 用户新增
	 * 
	 * @param sysUser   roleIds来源于新增用户所属机构分配的角色。分级管理员根据新增用户所属机构查询可分配的角色接口如下 findRolesByOrgId(SysUser sysUser)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/operate")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-用户管理-用户新增")
	@ApiOperation(value = "用户新增", notes = "")
	@ApiImplicitParam(name = "sysUser", value = "文档对象", required = true, paramType = "body", dataType = "SysUser")
	public ResultObject<?> save(@ApiParam @RequestBody @Valid SysUser sysUser) throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		sysUser.setCreateUserId(loginUser.getId());
		sysUser.setCreateUserName(loginUser.getUserName());

		if (sysUser.getRoleIds().isEmpty()) {
			return ResultObject.fail("请选择角色!");
		}
		
		SysUser user = sysUserService.saveUserAndRole(sysUser);
		return ResultObject.ok("新增成功", user);
	}

	/**
	 * 用户修改
	 * 
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/operate")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-用户管理-用户修改")
	@ApiOperation(value = "用户修改", notes = "")
	@ApiImplicitParam(name = "sysUser", value = "用户", required = true, paramType = "body", dataType = "SysUser")
	public ResultObject<?> update(@ApiParam @RequestBody @Valid SysUser sysUser) throws Exception {
		if (sysUser.getOrgId() == null) {
			return ResultObject.fail("缺少机构ID");
		}
		SysUserVo loginUser = this.getLoginUser();
		sysUser.setModifyUserId(loginUser.getId());
		sysUser.setModifyUserName(loginUser.getUserName());
		SysUser user = sysUserService.saveUserAndRole(sysUser);
		if (user != null) {
			return ResultObject.ok("修改成功!", user);
		}
		return ResultObject.fail("修改失败");
	}

	/**
	 * 用户删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-用户管理-用户删除")
	@ApiOperation(value = "用户删除", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject<?> delete(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择用户ID.");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser == null) {
			return ResultObject.fail("用户不存在，操作失败!");
		}
		sysUser.setModifyUserId(this.getLoginUserId());
		sysUser.setModifyUserName(this.getLoginUserName());
		sysUser.setDeleteState(CommonConstant.DELETE);
		sysUserService.update(sysUser);
		return ResultObject.ok("操作成功");
	}

	/**
	 * 根据ID获取用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-获取用户")
	@ApiOperation(value = "根据ID获取用户", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long") })
	public ResultObject<?> getUser(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择用户ID.");
		}
		SysUser sysUser = sysUserService.findUserAndRolesByUserId(id);
		if (sysUser != null) {
			return ResultObject.ok(sysUser);
		}
		return ResultObject.fail("查无此用户!");
	}

	/**
	 * 根据用户ID获取角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/operateRole/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-获取角色")
	@ApiOperation(value = "根据用户ID获取角色", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject<?> getRole(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择用户ID.");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser != null) {
			List<SysRole> roles = sysUserService.findSysRoleById(id);
			if (roles != null) {
				return ResultObject.ok(roles);
			}
			return ResultObject.fail("该用户尚未分配角色!");
		}
		return ResultObject.fail("查无此用户!");
	}
	
	/**
	 * 根据用户ID获取机构
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findOrgByUserId/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-查询机构")
	@ApiOperation(value = "根据用户ID查询机构", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject<?> findOrgByUserId(@PathVariable("id") Long id) throws Exception {
		if (id == null || id == 0) {
			return ResultObject.fail("请选择用户ID.");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser != null) {
			List<SysUserOrganVo> orgs = sysUserOrganService.findSysUserOrganVoListByUserId(id,sysUser.getOrgId());
			if (!CollectionUtils.isEmpty(orgs)) {
				return ResultObject.ok(orgs);
			}
			return ResultObject.fail("该用户尚未分配机构!");
		}
		return ResultObject.fail("查无此用户!");
	}

	
	/**
	 * 系统管理-用户管理-为用户修改机构（重新分配机构，对应机构设置，相当于增删改）
	 * 
	 * @param userId  sysOrganIds(如果权限ids为null,则相当于为用户删除所有机构)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/userOrganBind")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-机构管理-机构设置")
	@ApiOperation(value = "机构设置", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "令牌", required = true, paramType = "header", dataType = "string"),
			@ApiImplicitParam(name = "userId", value = "用户编号", required = true, paramType = "body"),
			@ApiImplicitParam(name = "sysOrganIds", value = "机构编号列表", paramType = "body") })
	public ResultObject<?> userOrganBind(@RequestParam("userId") Long userId,@RequestParam(value="sysOrganIds",required=false)String sysOrganIds) throws Exception {
		if (userId == null || userId < 0) {
			return ResultObject.fail("缺少参数userId");
		}
		sysUserOrganService.updateUserOrgan(userId, sysOrganIds);
		return ResultObject.ok("机构设置成功");
	}

	/**
	 * 启用用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/toUse")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-用户管理-启用")
	@ApiOperation(value = "启用用户", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query", dataType = "Long")
	public ResultObject<?> toUse(@RequestParam("id") Long id) throws Exception {
		if (id == null) {
			return ResultObject.fail("缺少ID");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser == null) {
			return ResultObject.fail("没有此用户!");
		}
		if (sysUser.getUserState() == CommonConstant.DESTROY_STATE) {
			return ResultObject.fail("用户已经注销，不能再启用!");
		}
		sysUser.setUserState(CommonConstant.USE_STATE);
		sysUserService.update(sysUser);
		return ResultObject.ok("启用成功!");
	}

	/**
	 * 停用用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/toStop")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-用户管理-停用")
	@ApiOperation(value = "停用用户", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query", dataType = "Long")
	public ResultObject<?> toStop(@RequestParam("id") Long id) throws Exception {
		if (id == null) {
			return ResultObject.fail("缺少ID");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser == null) {
			return ResultObject.fail("没有此用户!");
		}
		sysUser.setUserState(CommonConstant.STOP_STATE);
		sysUserService.update(sysUser);
		return ResultObject.ok("停用成功!");
	}

	/**
	 * 注销用户
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/toDestroy")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-用户管理-注销")
	@ApiOperation(value = "注销用户", notes = "")
	@ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query", dataType = "Long")
	public ResultObject<?> toDestroy(@RequestParam("id") Long id) throws Exception {
		if (id == null) {
			return ResultObject.fail("缺少ID");
		}
		SysUser sysUser = sysUserService.findOne(id);
		if (sysUser == null) {
			return ResultObject.fail("没有此用户!");
		}
		sysUser.setUserState(CommonConstant.DESTROY_STATE);
		sysUserService.update(sysUser);
		return ResultObject.ok("注销成功!");
	}

	/**
	 * 用户查询
	 * 
	 * @return
	 */
	@PostMapping(value = "/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-查询")
	@ApiOperation(value = "查询用户列表", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject<?> list() throws ParseException {
		Map<String, Object> paramsMap = getParameterMap();
		//添加条件 过滤已经删除的数据
		if (! paramsMap.containsKey("EQ_deleteState")) {
			paramsMap.put("EQ_deleteState",0);
		}
		PageRequest pageRequest = buildPageRequest(paramsMap);
		
		Page<SysUser> sysUsers = sysUserService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(sysUsers);
	}

	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-登录名查询用户")
	@GetMapping("/findByUserName/{username}")
	@ApiOperation(value = "根据登录名查询用户", notes = "")
	@ApiImplicitParam(name = "username", value = "username", required = true, paramType = "path", dataType = "string")
	public SysUserVo findByUserName(@PathVariable("username") String username) throws Exception {
		logger.info("username:{}",username);
		SysUser sysUser = sysUserService.findByUserName(username);
		if (sysUser == null) {
			return null;
		}
		SysUserOrganVo vo = sysUserOrganService.findUserOrganVoByUserId(sysUser.getId(), sysUser.getOrgId());
		SysUserVo sysUserVo = new SysUserVo();
		BeanUtils.copyProperties(sysUser, sysUserVo);
		if(vo != null) {
			sysUserVo.setIdentId(vo.getId());
			sysUserVo.setIdentName(vo.getOrgName());
		}
		return sysUserVo ;
	}
	
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-手机号码查询用户")
	@GetMapping("/findByMobile/{mobile}")
	@ApiOperation(value = "根据手机号码查询用户", notes = "")
	@ApiImplicitParam(name = "mobile", value = "mobile", required = true, paramType = "path", dataType = "string")
	public SysUserVo findByMobile(@PathVariable("mobile") String mobile) throws Exception {
		SysUser sysUser = sysUserService.findByMobile(mobile);
		if (sysUser == null) {
			return null;
		}
		SysUserOrganVo vo = sysUserOrganService.findUserOrganVoByUserId(sysUser.getId(), sysUser.getOrgId());
		SysUserVo sysUserVo = new SysUserVo();
		BeanUtils.copyProperties(sysUser, sysUserVo);
		if(vo != null) {
			sysUserVo.setIdentId(vo.getId());
			sysUserVo.setIdentName(vo.getOrgName());
		}
		return sysUserVo ;
	}
	
	/**
	 * 分级管理员根据新增用户所属机构查询可分配的角色
	 * 
	 * @return
	 */
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-用户管理-根据新增用户所属机构查询可分配的角色")
	@GetMapping("/findRolesByCurrentOrg")
	@ApiOperation(value = "根据新增用户所属机构查询可分配的角色", notes = "")
	public ResultObject<List<SysRole>> findRolesByOrgId(SysUser sysUser) throws Exception {		
		 long orgId =sysUser.getOrgId();
		 List<SysOrganRole> sors=organRoleService.findRolesByOrgId(orgId);
		 List<Long> rids=new ArrayList<>();
		 if(!CollectionUtils.isEmpty(sors)) {
		 for(SysOrganRole sor:sors) {
			Long rid= sor.getSysRoleId();
			rids.add(rid);
		 }
		 List<SysRole> sysRoles=roleService.findByIdIn(rids);
		 return ResultObject.ok(sysRoles);
		 }else {
			 return ResultObject.fail("尚未给当前机构分配角色!"); 
		 }
	}

}
