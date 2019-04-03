package com.dingxin.system.rpc;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.MqRestApiFallBack;

/**
 * @ClassName: MqRestApi
 * @Description: 对外提MQapi
 * @author xh
 * @date 2018年7月7日 下午6:17:48
 * 
 */
@FeignClient(name = "dingxin-mq-service/mq", fallback = MqRestApiFallBack.class, configuration = FeignConfig.class)
public interface MqRestApi {

	@RequestMapping(value = "/send/log", method = RequestMethod.POST)
	public ResultObject<SysOperateLogVo> sendLog(@RequestBody SysOperateLogVo logVo);

}
