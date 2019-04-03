package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.entity.SysOrgan;
import com.dingxin.system.service.organ.SysOrganService;
import com.dingxin.system.service.organrole.SysOrganRoleService;
import com.dingxin.system.util.OrgTreeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 系统管理-机构管理
 * 
 * @author shixh
 *
 */
@RestController
@RequestMapping("/org")
@Api(tags = "机构操作", description = "增删查改")
public class SysOrganControl extends BaseController {

	@Autowired
	private SysOrganService sysOrganService;

	@Autowired
	private  SysOrganRoleService organRoleService;
	/**
	 * 系统管理-机构管理-新增
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/save")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-机构管理-新增")
	@ApiOperation(value = "机构新增", notes = "")
	@ApiImplicitParam(name = "entity", value = "机构", required = true, paramType = "body", dataType = "string")
	public ResultObject save(@ApiParam @RequestBody @Valid SysOrgan entity) throws Exception {
		entity.setCreateUserId(this.getLoginUserId());
		entity.setCreateUserName(this.getLoginUserName());
		return sysOrganService.checkAndSave(entity);
	}

	/**
	 * 系统管理-机构管理-修改
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/update")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-机构管理-修改")
	@ApiImplicitParam(name = "entity", value = "机构", required = true, paramType = "body", dataType = "string")
	public ResultObject update(@ApiParam @RequestBody @Valid SysOrgan entity) throws Exception {

		if (entity.getId() == null || entity.getId() == 0) {
			return ResultObject.fail("机构Id不存在，无法修改");
		}

		entity.setModifyUserId(this.getLoginUserId());
		entity.setModifyUserName(this.getLoginUserName());
		return sysOrganService.checkAndUpdate(entity);
	}

	/**
	 * 系统管理-机构管理-删除
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/delete/{ids}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-机构管理-删除")
	@ApiOperation(value = "根据机构IDS删除机构", notes = "")
	@ApiImplicitParam(name = "ids", value = "机构IDS", required = true, paramType = "path", dataType = "String")
	public ResultObject delete(@NotNull @PathVariable("ids") String ids) throws Exception {
		return sysOrganService.deleteByIds(ids);
	}

	/**
	 * 系统管理-机构管理-查询
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-机构管理-查询")
	@ApiOperation(value = "查询机构列表(带分页)", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject<?> list() throws ParseException {
		Map<String, Object> paramsMap = getParameterMap();
		// searchParams.put("EQ_userName","show");//自定义添加条件
		PageRequest pageRequest = buildPageRequest(paramsMap);
		Page<SysOrgan> sysOrgans = sysOrganService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(sysOrgans);
	}

	/**
	 * 系统管理-机构管理-根据父节点查询 动态机构树查询（每点击一次上级按钮，弹出下属机构）
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/search/{parentId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-机构管理-根据父节点查询")
	@ApiOperation(value = "根据父节点查询查询机构列表", notes = "")
	@ApiImplicitParam(name = "parentId", value = "父级编号", required = true, paramType = "path", dataType = "long")
	public ResultObject search(@NotNull @PathVariable(value = "parentId") long parentId) throws Exception {
		List<TreeBean> ots= sysOrganService.queryOrgTreeByParentId(parentId);
		return ResultObject.ok(CollectionUtils.isEmpty(ots) ? "查无数据" : ots);
	}

	
	/**
	 * 系统管理-机构管理-静态机构树查询 采用递归解析成树形结构，同时将对象sysOrgan转化为OrganTree对象返回前端 *
	 * 
	 * @param
	 * @return
	 * @author luozb
	 */
	@GetMapping("/staticTree")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-机构管理-静态机构树查询")
	@ApiOperation(value = "静态机构树查询", notes = "")
	@ApiImplicitParam(name = "Authorization", value = "令牌", required = true, paramType = "header", dataType = "string")
	public ResultObject searchAllOrgTree() {
		List<TreeBean> tbs =sysOrganService.searchAllOrgTree();		
		return ResultObject.ok(tbs);
	}

	/**
	 * 系统管理-机构管理-根据机构id查询组织机构
	 * 
	 * @param Id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findById/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-机构管理-根据机构id查询组织机构")
	@ApiOperation(value = "根据机构id查询组织机构", notes = "")
	@ApiImplicitParam(name = "id", value = "编号", required = true, paramType = "path", dataType = "long")
	public ResultObject findById(@NotNull @PathVariable(value = "id") long id) throws Exception {
		SysOrgan sysOrgan = sysOrganService.findOne(id);
	 	return ResultObject.ok(sysOrgan);
	}
	
	
	
	/**
	 * 系统管理-机构管理-机构树查询（此方法适合于orcal或dm数据库）
	 * 
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchAll/{parentId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-机构管理-机构树查询")
	@ApiImplicitParam(name = "parentId", value = "父级编号", required = true, paramType = "path", dataType = "long")
	public ResultObject searchAll(@NotNull @PathVariable(value = "parentId") long parentId) throws Exception {
		List<SysOrgan> sysOrgans = sysOrganService.findByParentId(parentId);
		return ResultObject.ok(CollectionUtils.isEmpty(sysOrgans) ? "查无数据" : OrgTreeUtil.toTree(sysOrgans, parentId));
	}

	/**
	 * 系统管理-机构管理-获取登录用户授权范围
	 * 
	 * @param 查询登录用户是否可授权，可给哪些机构授权
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getLoginUserAuth")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-机构管理-获取登录用户授权范围")
	@ApiOperation(value = "获取登录用户授权范围", notes = "")
	public ResultObject getLoginUserAuth() throws Exception {
		SysUserVo loginUser = this.getLoginUser();	
		List<TreeBean> tbs =organRoleService.getLoginUserAuth(loginUser);		
		if(tbs==null || CollectionUtils.isEmpty(tbs)) {
			return ResultObject.fail("当前登录用户尚未分配角色或者无管理员分配角色权限！");
		}
		return ResultObject.ok("该登录用户可以授权，授权范围见返回对象!", tbs);
	}
	
	
	/**
	 * 系统管理-机构管理-为机构修改角色（重新分配角色，对应角色设置，相当于增删改）
	 * 如果是admin，可以传任意机构id,任意角色。
	 * @param sysOrganId sysRoleIds  (如果sysRoleIds为null,则相当于为机构删除所有权限)
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/organRoleBind")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-机构管理-角色设置")
	@ApiOperation(value = "角色设置", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "sysOrganId", value = "机构编号", required = true, paramType = "body"),
			@ApiImplicitParam(name = "sysRoleIds", value = "角色编号列表", paramType = "body") })
	public ResultObject organRoleBind(@RequestParam("sysOrganId") Long sysOrganId,@RequestParam(value="sysRoleIds",required=false)String sysRoleIds) throws Exception {
		if (sysOrganId == null || sysOrganId < 0) {
			return ResultObject.fail("缺少参数sysOrganId");
		}
		SysUserVo loginUser = this.getLoginUser();				
			return organRoleService.updateOrganRole(loginUser,sysOrganId, sysRoleIds);			
		
	}
	
}
