package com.dingxin.system.controller;

import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.system.entity.SysUser;

/**
 * common controller
 * @author shixh
 */
public class UserBaseController extends BaseController{

	/**
	 * getLoginUser
	 * @author shixh
	 * @return
	 * @throws Exception
	 */
	public SysUser getLoginSysUser() throws Exception {
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		String jsonUser = (String)valueOperations.get(this.getUserToken()+RedisKeyConstant.USER_KEY);
		SysUser user = JSON.parseObject(jsonUser,SysUser.class);
		if(user==null) {
			throw new RestException(USER_NULL_MSG);
		}
		return user;
	}

}
