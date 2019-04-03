package com.dingxin.system.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.dingxin.system.entity.SysDictItem;
import com.dingxin.system.service.dictionary.SysDictItemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


/**  
* @ClassName: SysDictItemControl  
* @Description: 数据字典明细表操作  
* @author luozb  
* @date 2018年6月14日下午13:31:24  
*    
*/
@RestController
@RequestMapping("/dictItem")
@Api(tags = "字典明细表操作", description = "增删改查")
public class SysDictItemControl extends BaseController {

	@Autowired
	SysDictItemService sysDictItemService;

	/**
	 * 字典明细新增
	 * 
	 * @param SysDictItem
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/operate")
	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-数据字典-字典类型新增")
	@ApiOperation(value = "字典类型新增", notes = "")
	@ApiImplicitParam(name = "sysDictItem", value = "字典类型", required = true, paramType = "body", dataType = "SysDictGroup")
	public ResultObject save(@ApiParam @RequestBody @Valid SysDictItem sysDictItem) throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		sysDictItem.setCreateUserId(loginUser.getId());
		sysDictItem.setCreateUserName(loginUser.getUserName());
		return sysDictItemService.saveSysDictItem(loginUser,sysDictItem);	
	}

	
	/**
	 * 字典明细修改
	 * 
	 * @param sysDictItem
	 * @return
	 * @throws Exception
	 */
	@PutMapping("/operate")
	@UserOperate(name = OperateConstant.UPDATE, business = "系统管理-数据字典-字典明细修改")
	@ApiOperation(value = "字典明细修改", notes = "")
	@ApiImplicitParam(name = "sysDictItem", value = "字典类型", required = true, paramType = "body", dataType = "SysDictGroup")
	public ResultObject update(@ApiParam @RequestBody @Valid SysDictItem sysDictItem) throws Exception {
		SysUserVo loginUser = this.getLoginUser();
		sysDictItem.setModifyUserId(loginUser.getId());
		sysDictItem.setModifyUserName(loginUser.getUserName());
		return sysDictItemService.saveSysDictItem(loginUser,sysDictItem);	
	}

	/**
	 * 字典明细删除
	 * 
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping("/operate/{ids}")
	@UserOperate(name = OperateConstant.DELETE, business = "系统管理-数据字典-字典明细删除")
	@ApiOperation(value = "字典明细删除", notes = "")
	@ApiImplicitParam(name = "ids", value = "字典明细IDS", required = true, paramType = "path", dataType = "String")
	public ResultObject delete(@PathVariable("ids") String ids) throws Exception {		
		SysUserVo loginUser = this.getLoginUser();
		return sysDictItemService.deleteByIds(loginUser,ids);
		
	}

	/**
	 * 根据ID查看字典类型
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/operate/{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-字典类型查看")
	@ApiOperation(value = "根据ID查看字典类型", notes = "")
	@ApiImplicitParam(name = "id", value = "字典类型ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject getDictGroup(@PathVariable("id") @Valid Long id) throws Exception {
		SysDictItem sdg = sysDictItemService.findByIdAndDeleteState(id,CommonConstant.NORMAL);
		if (sdg != null) {
			return ResultObject.ok(sdg);
		}
		return ResultObject.fail("查无此字典类型!");
	}
	
	/**
	 * 根据groupCode查看字典类型的item
	 * 
	 * @param groupCode
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchByCode/{groupCode}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-查看类型字典值")
	@ApiOperation(value = "根据groupCode查看类型字典值", notes = "")
	@ApiImplicitParam(name = "groupCode", value = "字典类型ID", required = true, paramType = "path", dataType = "String")
	public ResultObject getItemByGroupCode(@PathVariable("groupCode") @Valid String groupCode) throws Exception {
		List<SysDictItem> list = sysDictItemService.findByGroupCodeAndDeleteState(groupCode,CommonConstant.NORMAL);
		if (list != null && list.size() > 0) {
			return ResultObject.ok(list);
		}
		return ResultObject.fail("查无此字典类型!");
	}
	
	/**
	 * 根据groupId查看字典类型的item
	 * 
	 * @param groupCode
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/searchById/{groupId}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-查看类型字典值")
	@ApiOperation(value = "根据groupId查看类型字典值", notes = "")
	@ApiImplicitParam(name = "groupId", value = "字典类型ID", required = true, paramType = "path", dataType = "Long")
	public ResultObject getItemByGroupId(@PathVariable("groupId") @Valid long groupId) throws Exception {
		List<SysDictItem> list = sysDictItemService.findByGroupIdAndDeleteState(groupId, CommonConstant.NORMAL);
		if (list != null && list.size() > 0) {
			return ResultObject.ok(list);
		}
		return ResultObject.fail("查无此字典类型!");
	}

	
	/**
	 * 系统管理-字典管理-字典明细查询
	 * @return
	 * @throws ParseException
	 */
	@PostMapping("/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-数据字典-字典明细查询")
	@ApiOperation(value = "查询字典明细列表", notes = "")
	@ApiImplicitParam(name = "", value = "任意条件", required = true, paramType = "form")
	public ResultObject<SysDictItem> list() throws Exception {
		Map<String, Object> paramsMap = getParameterMap();
		PageRequest pageRequest = buildPageRequest(paramsMap);
		paramsMap.put("EQ_deleteState", 0);
		Page<SysDictItem> pages = sysDictItemService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(pages);
	}

}
