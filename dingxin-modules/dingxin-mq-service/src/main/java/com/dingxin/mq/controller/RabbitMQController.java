package com.dingxin.mq.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.mq.service.RabbitMQService;

@RestController
public class RabbitMQController {
	
	@Autowired
	private RabbitMQService rabbitMQService;
	
	@PostMapping("/send/log")
	public ResultObject<SysOperateLogVo> sendLog(@RequestBody SysOperateLogVo logVo) {
		try {
			rabbitMQService.send(CommonConstant.LOGQUEUE, logVo);
		}catch (AmqpException e) {
			e.printStackTrace();
			return ResultObject.fail("发送失败");
		}
		return ResultObject.ok(logVo);
	}

}
