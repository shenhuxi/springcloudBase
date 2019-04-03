package com.dingxin.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dingxin.common.constant.CommonConstant;

/**
 * RabbitMQ的配置类，用来配队列、交换器、路由等高级信息
 *
 * @author xh
 * @date 2018年6月27日 上午11:42:19 
 *
 */

@Configuration
public class RabbitConfig {
	@Bean
    public Queue helloConfig(){
        return new Queue(CommonConstant.LOGQUEUE);
    }
}
