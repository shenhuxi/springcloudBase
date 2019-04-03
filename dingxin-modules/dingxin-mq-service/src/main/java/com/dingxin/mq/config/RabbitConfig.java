package com.dingxin.mq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dingxin.common.constant.CommonConstant;

/**
 * 配置类
 *
 * @author xh
 * @date 2018年7月5日 上午10:31:33 
 *
 */
@Configuration
public class RabbitConfig {

	/**
	 * 初始化日志队列
	 * @return
	 */
	@Bean
    public Queue queueConfig(){
        return new Queue(CommonConstant.LOGQUEUE);
    }
	
}