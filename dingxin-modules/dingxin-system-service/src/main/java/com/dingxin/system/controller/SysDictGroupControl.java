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
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.entity.SysDictGroup;
import com.dingxin.system.service.dictionary.SysDictGroupService;
import com.dingxin.system.vo.SysDictGroupItemVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**  
* @ClassName: SysDictGroupControl  
* @Description: 数据字典组操作  
* @author luozb  
* @date 2018年6月14日 上午9:31:24  
*    
*/
@RestController
@RequestMapping("/dictGroup")
@Api(tags = "字典组操作", description = "增删改查")
public class SysDictGroupControl extends BaseController {

	@Autowired
	SysDictGroupService sysDictGroupService;
	
	/**
	 * 字典组新增
	 * 
	 * @param sysDictGroup
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/operate")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-数据字典-字典组新增")
	@ApiOperation(value = "字典组新增", notes = "")
	@ApiImplicitParam(name = "sysDictGroup", value = "字典组", required = true, paramType = "body", dataType = "SysDictGroup")
	public ResultObject save(@ApiParam @RequestBody @Valid SysDictGroup sysDictGroup) throws Exception {
		SysUserVo sysUser = this.getLoginUser();		
		return sysDictGroupService.saveSysDictGroup(sysUser,sysDictGroup);	
	}

	
	/**
	 * 字典组修改
	 * 
	 * @param sysDictGroup
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/operate")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-数据字典-字典组修改")
	@ApiOperation(value = "字典组修改", notes = "")
	@ApiImplicitParam(name = "sysDictGroup", value = "字典组", required = true, paramType = "body", dataType = "SysDictGroup")
	public ResultObject update(@ApiParam @RequestBody @Valid SysDictGroup sysDictGroup) throws Exception {
		SysUserVo sysUser = this.getLoginUser();		
		return sysDictGroupService.saveSysDictGroup(sysUser,sysDictGroup);	
	}

	/**
	 * 字典组删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-数据字典-字典组删除")
	@ApiOperation(value = "字典组删除", notes = "")
	@ApiImplicitParam(name = "id", value = "字典组ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject delete(@PathVariable("id") Long id) throws Exception {		
		SysUserVo loginUser = this.getLoginUser();
		return sysDictGroupService.deleteById(loginUser,id);
		
	}

	/**
	 * 根据ID查看字典组
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-字典组查看")
	@ApiOperation(value = "根据ID查看字典组", notes = "")
	@ApiImplicitParam(name = "id", value = "字典组ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject getDictGroup(@PathVariable("id") @Valid Long id) throws Exception {
		SysDictGroup sdg = sysDictGroupService.findOne(id);
		if (sdg != null) {
			return ResultObject.ok(sdg);
		}
		return ResultObject.fail("查无此字典组!");
	}
	
	/**
	 * 系统管理-字典管理-字典组查询
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-字典组查询")
	@ApiOperation(value = "查询字典组列表", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject<?> list() throws Exception {
		Map<String, Object> paramsMap = getParameterMap();
		PageRequest pageRequest = buildPageRequest(paramsMap);
		paramsMap.put("EQ_deleteState", 0);
		Page<SysDictGroup> pages = sysDictGroupService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(pages);
	}

	
	/**
	 * 系统管理-字典管理-根据父节点查询 动态字典树查询（每点击一次上级按钮，弹出下级字典）
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/search/{parentId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-字典管理-根据父节点查询")
	@ApiOperation(value = "根据父节点查询字典列表", notes = "")
	@ApiImplicitParam(name = "parentId", value = "父级编号", required = true, paramType = "path", dataType = "long")
	public ResultObject search(@NotNull @PathVariable(value = "parentId") long parentId) throws Exception {
		List<TreeBean> ots= sysDictGroupService.queryDictTreeByParentId(parentId);
		return ResultObject.ok(CollectionUtils.isEmpty(ots) ? "查无数据" : ots);
	}

	
	/**
	 * 系统管理-字典管理-静态字典类型树查询 采用递归解析成树形结构，同时将对象sysDictGroup转化为dictTree对象返回前端 *
	 * (Redis缓存)
	 * 
	 * @param 
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/staticTree")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-字典管理-静态字典树查询")
	@ApiOperation(value = "静态字典树查询", notes = "")
	@ApiImplicitParam(name = "Authorization", value = "令牌", required = true, paramType = "header", dataType = "string")
	public ResultObject searchAllDictTree() {
		List<TreeBean> tbs =sysDictGroupService.searchAllDictTree();		
		return ResultObject.ok(tbs);
	}
	
	/**
	 * 根据字典类型ID获取明细(此方法暂时有bug.)
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/findItemByDictId/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-字典类型明细获取")
	@ApiOperation(value = "根据字典类型ID获取字典明细", notes = "")
	@ApiImplicitParam(name = "id", value = "字典类型ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject findItemByDictId(@PathVariable("id") @Valid Long id) throws Exception {
		List<SysDictGroupItemVO> items = sysDictGroupService.findByIdAndDeleteState(id,CommonConstant.NORMAL);
		if (!CollectionUtils.isEmpty(items)) {
				return ResultObject.ok(items);
			}
			return ResultObject.fail("该字典类型尚未增加明细数据!");
		
	}
}
