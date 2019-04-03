package com.dingxin.mq.service;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQService {

	@Autowired
	private AmqpTemplate amqpTemplate;

	/**
	 * 发送消息到队列
	 * @param queueName
	 * @param obj
	 * @throws AmqpException
	 */
	public void send(String queueName, Object obj) throws AmqpException{
		amqpTemplate.convertAndSend(queueName, obj);
	}

}
