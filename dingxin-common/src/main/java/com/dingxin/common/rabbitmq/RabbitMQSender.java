package com.dingxin.common.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.vo.log.SysOperateLogVo;

@Component
public class RabbitMQSender {

	@Autowired
	private AmqpTemplate amqpTemplate;

	public void sender(SysOperateLogVo sysOperateLogVo) {
		amqpTemplate.convertAndSend(CommonConstant.LOGQUEUE, sysOperateLogVo);
	}
}
