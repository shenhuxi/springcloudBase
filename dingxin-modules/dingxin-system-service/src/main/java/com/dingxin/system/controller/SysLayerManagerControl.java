package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.system.entity.SysLayerManager;
import com.dingxin.system.service.layer.SysLayerManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统管理-分级管理员
 * 分级管理设计思路：
 * 1.在不影响原代码的前提下，新增角色分级，且每个分级管理员的授权范围（layer）可根据需求在分级管理员表中任意配置。
 * 2.超级管理员可以给顶级机构，任意用户，分级机构授权。
 * 3.分级管理员可以给下级机构，机构内人员授权。
 * 分级管理开发步骤：
 * 1.建立分级管理员基本信息表（增删改查）
 * 2.上级机构的分级管理员按需求将某些角色划拨给下级机构的分级管理员（角色设置），从操作上来说，这一步主要是指超级管理员给顶级机构分配角色。实现方式为新建一个机构角色关联表。
 * 3.分级管理员授权
 *     @可否授权---只有分级管理员才能授权，普通用户不能授权，且授权的范围为分级管理员表中的layer字段对应的层数.
 *   a.根据当前登录用户的身份（分级管理员）获取分级管理员信息
 *   b.根据传入的orgid（需要给授权的用户的机构id）以及分级管理员信息的layer层次，查询所有orgids,并将其作树状结构显示出来。
 *   c.如果某机构包含于orgids中，则可以给该机构授权，否则不能给其授权。
 *     @授权---上级分级管理员给本级机构和下级机构分配角色。
 *   a.分级管理员在点击角色设置按钮为本部门或下级部门分配角色时，左侧选择栏为机构角色关联表中上级分级管理员给本级分级管理员分配的角色（根据用户的的orgid与orgRole关联查询，查出所有的角色）。
 *   b.分级管理员在新增用户时，为用户关联角色，角色也来源于机构角色关联表中上级分级管理员给本级分级管理员分配的角色（根据用户的的orgid与orgRole关联查询，查出所有的角色）。
 * 注：根据orgid和layer层次，查询所有orgids，如果要作树状结构显示可授权的机构出来可用递归方法，
 *    如果要显示任意节点任意层数的所有orgids，这里采用的是mysql自定义函数，详见SysOrganRepository类中的备注。
 *    上生产环境或预发布环境时须记得将该函数脚本导入到mysql中执行一遍。
 * @author luozb
 *
 */
@RestController
@RequestMapping("/sysLayerManager")
@Api(tags = "分级管理员操作", description = "增删查改")
public class SysLayerManagerControl extends BaseController {

	@Autowired
	private SysLayerManagerService sysLayerManagerService;

	
	/**
	 * 系统管理-分级管理员-新增
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/save")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-分级管理员-新增")
	@ApiOperation(value = "分级管理员新增", notes = "")
	@ApiImplicitParam(name = "entity", value = "分级管理员", required = true, paramType = "body", dataType = "string")
	public ResultObject save(@ApiParam @RequestBody @Valid SysLayerManager entity) throws Exception {	
		return sysLayerManagerService.checkAndSave(entity);
	}

	/**
	 * 系统管理-分级管理员-修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/update")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-分级管理员-修改")
	@ApiImplicitParam(name = "entity", value = "分级管理员", required = true, paramType = "body", dataType = "string")
	public ResultObject update(@ApiParam @RequestBody @Valid SysLayerManager entity) throws Exception {
		return sysLayerManagerService.checkAndUpdate(entity);
	}

	/**
	 * 系统管理-分级管理员-删除
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/delete/{ids}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-分级管理员-删除")
	@ApiOperation(value = "根据分级管理员IDS删除分级管理员", notes = "")
	@ApiImplicitParam(name = "ids", value = "分级管理员IDS", required = true, paramType = "path", dataType = "String")
	public ResultObject delete(@NotNull @PathVariable("ids") String ids) throws Exception {
		return sysLayerManagerService.deleteByIds(ids);
	}

	/**
	 * 系统管理-分级管理员-查询
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-分级管理员-查询")
	@ApiOperation(value = "查询分级管理员列表(带分页)", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject<?> list() throws ParseException {
		Map<String, Object> paramsMap = getParameterMap();
		// searchParams.put("EQ_userName","show");//自定义添加条件
		PageRequest pageRequest = buildPageRequest(paramsMap);
		Page<SysLayerManager> sysLayerManagers = sysLayerManagerService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(sysLayerManagers);
	}

	
	/**
	 * 系统管理-分级管理员-查询
	 * 
	 * @return 不带分页但按sort字段降序排列返回查询到的所有分级管理员
	 */
	@PostMapping(value = "/listNoPage")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-分级管理员-查询（不分页）")
	@ApiOperation(value = "查询分级管理员列表(按sort字段降序,不带分页)", notes = "")
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
		
		List<SysLayerManager> slms = sysLayerManagerService.findAll(paramsMap);
		return ResultObject.ok(slms);
	}
	/**
	 * 系统管理-分级管理员-根据分级管理员id查询分级管理员
	 * 
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findById/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-分级管理员-根据分级管理员id查询分级管理员")
	@ApiOperation(value = "根据分级管理员id查询分级管理员", notes = "")
	@ApiImplicitParam(name = "id", value = "编号", required = true, paramType = "path", dataType = "long")
	public ResultObject findById(@NotNull @PathVariable(value = "id") long id) throws Exception {
		SysLayerManager slm = sysLayerManagerService.findOne(id);
	 	return ResultObject.ok(slm);
	}
	
}
