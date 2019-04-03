package com.dingxin.oauth2.controller;

import static com.dingxin.common.constant.Oauth2Constant.TOKEN_SPLIT;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.oauth2.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
* Title: LoginControl 
* Description:  系统管理-用户登陆管理
* @author dicky  
* @date 2018年6月26日 下午12:04:19
 */
@RestController
@RequestMapping("/login")
@Api(tags = "认证登录", description = "LoginControl")
public class LoginControl extends BaseController {
	
	@Autowired
	private AuthService authService;
	

	@ApiOperation(value = "退出登录", notes = "")
	@GetMapping(value = "/logout")
    @UserOperate(name=OperateConstant.LOGOUT,business="系统管理-认证授权-退出")
    public ResultObject<?> logout(
    		@RequestHeader(value = "Authorization", required = true) String authorization,  
    		@RequestParam("userName") String userName) throws Exception {
    	if(StringUtils.isBlank(authorization) || StringUtils.isBlank(userName)) {
            return ResultObject.fail("参数异常!");
        }
    	authorization = authorization.replace(TOKEN_SPLIT,"");
    	boolean flag = authService.logout(authorization);
    	if(flag) {
    		logger.info("{}退出登录成功！",userName);
    		redisTemplate.delete(userName+RedisKeyConstant.USER_KEY);
    	}
    	return ResultObject.ok();
    }

}
