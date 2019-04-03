package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

import com.alibaba.fastjson.JSON;
import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.DateUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.MenuTree;
import com.dingxin.common.vo.system.SysPermissionVo;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.entity.SysPermission;
import com.dingxin.system.service.permission.SysPermissionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统管理-权限管理
 * 
 * @author shixh
 *
 */
@RestController
@RequestMapping("/permission")
@Api(tags = "权限操作", description = "增删查改")
public class SysPermissionControl extends BaseController {

	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private Environment env;

	/**
	 * 系统管理-权限管理-新增权限
	 * 
	 * @param sysPermission
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/operate")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-权限管理-新增权限")
	@ApiOperation(value = "权限新增", notes = "")
	public ResultObject save(@ApiParam @RequestBody @Valid SysPermission sysPermission) {
		SysUserVo loginUser = null;
		ResultObject resultObject = null;
		try {
			loginUser = this.getLoginUser();
			sysPermission.setCreateUserId(loginUser.getId());
			sysPermission.setCreateUserName(loginUser.getUserName());
			resultObject = sysPermissionService.checkAndSave(sysPermission);
		} catch (Exception e) {
			resultObject = ResultObject.fail(e.getMessage());
			e.printStackTrace();
		}
		return resultObject;
	}

	/**
	 * 系统管理-权限管理-修改权限
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/operate")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-权限管理-修改权限")
	@ApiOperation(value = "权限修改", notes = "")
	@ApiImplicitParam(name = "SysPermission", value = "权限", required = true, paramType = "body", dataType = "string")
	public ResultObject update(@ApiParam @RequestBody @Valid SysPermission entity) {
		SysUserVo loginUser = null;
		ResultObject resultObject = null;
		try {
			loginUser = this.getLoginUser();
			entity.setModifyUserId(loginUser.getId());
			entity.setModifyUserName(loginUser.getUserName());
			SysPermission sysPermission = sysPermissionService.checkAndUpdate(entity);
			resultObject = ResultObject.ok("修改成功!", sysPermission);
		} catch (Exception e) {
			e.printStackTrace();
			resultObject = ResultObject.fail(e.getMessage());
		}
		return resultObject;
	}

	/**
	 * 系统管理-权限管理-删除权限
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/operate/{ids}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-权限管理-删除权限")
	@ApiOperation(value = "根据权限ID删除权限", notes = "")
	@ApiImplicitParam(name = "ids", value = "权限IDS", required = true, paramType = "path", dataType = "Long")
	public ResultObject delete(@NotNull @PathVariable("ids") String ids) throws Exception {
		return sysPermissionService.deleteByIds(ids);
	}


	/**
	 * 系统管理-权限管理-查询
	 * (根据权限id查询权限与该方法合并）
	 * @return
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-查询")
	@ApiOperation(value = "查询权限列表", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject list() {
		Map<String, Object> paramsMap = getParameterMap();
		PageRequest pageRequest = buildPageRequest(paramsMap);
		Page<SysPermissionVo> pages = sysPermissionService.getPage(paramsMap, pageRequest);
		return ResultObject.ok(pages);
	}

	/**
	 * 系统管理-权限管理-根据父节点查询
	 * 
	 * @param parentId
	 * @return 动态权限树查询，每点击一次弹出子节点
	 * @throws Exception
	 */
	@GetMapping("/search/{parentId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-根据父节点查询")
	@ApiOperation(value = "根据父节点查询查询权限列表", notes = "")
	@ApiImplicitParam(name = "parentId", value = "父级编号", required = true, paramType = "path")
	public ResultObject search(@NotNull @PathVariable(value = "parentId") long parentId) throws Exception {
		List<TreeBean> pts = sysPermissionService.queryPerTreeByParentId(parentId);
		return ResultObject.ok(CollectionUtils.isEmpty(pts) ? "查无数据" : pts);
	}

	/**
	 * 系统管理-权限管理-静态权限树查询 采用递归解析成树形结构，同时将对象SysPermission转化为TreeBean对象返回前端 *
	 * 
	 * @param
	 * @return
	 * @author luozb
	 */
	@GetMapping("/searchAllTree")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-静态权限树查询")
	@ApiOperation(value = "静态权限树查询", notes = "")
	public ResultObject searchAllTree() {
		Map<String, Object> paramsMap = getParameterMap();
		List<TreeBean> trees =sysPermissionService.searchAllPerTree(paramsMap);		
		return ResultObject.ok(trees);
		
	}

	/**
	 * 系统管理-权限管理-静态权限树查询 采用递归解析成树形结构，同时将对象SysPermission转化为MenuTree对象返回前端 *
	 * 
	 * @param
	 * @return
	 * @author luozb
	 * @throws Exception 
	 */
	@PostMapping("/searchMenuTree")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-静态菜单树查询")
	@ApiOperation(value = "静态菜单树查询", notes = "")
	public ResultObject searchMenuTree() throws Exception {
		Map<String, Object> paramsMap = getParameterMap();
		List<MenuTree> menus =sysPermissionService.getMenuTree(paramsMap);		
		return ResultObject.ok(menus);
		
	}	

	
//	/**
//	 * 系统管理-权限管理-权限树查询(此方法适用于orcal或dm)
//	 *
//	 * @param parentId
//	 * @return 静态权限树
//	 * @throws Exception
//	 */
//	@GetMapping("/searchAll/{parentId}")
//	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-权限树查询")
//	@ApiOperation(value = "权限树查询", notes = "")
//	@ApiImplicitParam(name = "parentId", value = "父级编号", required = true, paramType = "path")
//	public ResultObject searchAll(@NotNull @PathVariable(value = "parentId") long parentId) throws Exception {
//		List<SysPermission> sysPermissions = sysPermissionService.queryTreeByParentId(parentId);
//		return ResultObject.ok(
//				CollectionUtils.isEmpty(sysPermissions) ? "查无数据" : PermissionTreeUtil.toTree(sysPermissions, parentId));
//	}

	@PostMapping(value = "/gen")
	@ApiOperation(value = "脚本生成", notes = "Feign调用脚本生成")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-权限管理-脚本生成")
	public ResultObject permissionGen(@RequestParam("perjson") String perjson, @RequestParam("appName")String appName) throws RestException{
		logger.info("happenTime:{},event:Feign服务端脚本生成,input:{}", DateUtil.getNowYYYYMMDDHHMMSS(), JSON.toJSONString(perjson));

		try {
			List<SysPermission> list = JSON.parseArray(perjson,SysPermission.class);
			sysPermissionService.gen(list,appName);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new RestException("脚本生成异常");
		}
		return ResultObject.ok();
	}

	@GetMapping("/findRoles/{userId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-权限管理-查询用户权限")
	@ApiOperation(value = "查询用户权限", notes = "")
	@ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "path")
	public List<SysPermission> findRoles(@PathVariable Long userId) {
		return sysPermissionService.findRoles(userId);
	}
}
