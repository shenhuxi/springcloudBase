package com.dingxin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.DateUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.entity.SysOperateLog;
import com.dingxin.service.SysOperateLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 描述: 系统管理-日志管理
 * 作者: qinzhw
 * 创建时间: 2018/6/19 15:44
 */
@RestController
@RequestMapping("/log")
@Api(tags = "日志管理", description = "日志保存查询")
public class SysOperateLogControl extends BaseController {

	@Autowired
	private SysOperateLogService operateLogService;


	@PostMapping(value = "/list")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-日志管理-日志查询")
	@ApiOperation(value = "查询用户列表", notes = "")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "EQ_userName", value = "用户名", required = false, paramType = "query"),
		@ApiImplicitParam(name = "LIKE_operateBusiness", value = "操作业务", required = false, paramType = "query")
	})
	public ResultObject<?> list() {
		Map<String, Object> paramsMap = getParameterMap();
		PageRequest pageRequest = buildPageRequest(paramsMap);
		Page<SysOperateLog> sysLogs = operateLogService.findAll(paramsMap, pageRequest);
		return ResultObject.ok(sysLogs);
	}

	@GetMapping("{id}")
	@UserOperate(name = OperateConstant.SEARCH, business = "系统管理-日志管理-日志获取")
	@ApiOperation(value = "日志获取", notes = "")
	@ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path")
	public ResultObject<?> findById(@PathVariable Long id) throws IllegalAccessException {
		logger.info("happenTime:{},event:日志获取，param:{}", DateUtil.getNowYYYYMMDDHHMMSS(),id);
		return ResultObject.ok(operateLogService.findOne(id));
	}

//	@PostMapping
//	@UserOperate(name = OperateConstant.SAVE, business = "系统管理-日志管理-插入日志")
//	@ApiOperation(value = "插入日志", notes = "")
//	@ApiImplicitParam(name = "sysOperateLog", value = "插入日志", required = true, paramType = "body", dataType = "SysOperateLog")
//	public ResultObject add(@RequestBody @Valid SysOperateLog sysOperateLog) throws Exception {
//		if (sysOperateLog.getUserId() == null) {
//			SysUserVo user = getLoginUser();
//			if (user != null) {
//				sysOperateLog.setUserId(user.getId());
//				sysOperateLog.setUserName(user.getUserName());
//			}
//		}
//		SysOperateLog log = operateLogService.save(sysOperateLog);
//		return ResultObject.ok("新增成功", log);
//	}
}
