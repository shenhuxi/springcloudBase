package com.dingxin.common.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.constants.RedisKeyConstant;
import com.dingxin.model.ResultObject;
import com.dingxin.util.AuthCodeUtil;
import com.dingxin.util.AuthCodeUtil.Validate;
import com.dingxin.util.WebUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;

/**
 * 
* Title: CommonController 
* Description:  公共资源
* @author dicky  
* @date 2018年6月29日 上午11:22:31
 */

@Api(tags = "公共资源", description = "CommonController")
@RestController
@RequestMapping("/common")
public class CommonController {
	
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
    /**
     * 获取验证码 接口 
     * @param 默认是点击验证码图片刷新新验证码。
     * @return
     * @throws Exception
     */
	@ApiOperation(value = "获取验证码", notes = "")
    @GetMapping(value = "/getAuthCode")
    public ResultObject<?> getAuthCode(HttpServletRequest request) throws Exception {       	
    	String key = WebUtils.getRequestIp(request) + RedisKeyConstant.USER_AUTHCODE_KEY;
    	Validate code= AuthCodeUtil.getRandomCode(); 
    	ValueOperations<String, Object> operations = redisTemplate.opsForValue();
    	operations.set(key, JSONObject.fromObject(code).toString(), RedisKeyConstant.TIME_MINUTES_AuthCode, TimeUnit.SECONDS);
        return ResultObject.ok("获取成功",code);
    }
    

	 
}