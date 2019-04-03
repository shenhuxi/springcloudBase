package com.dingxin.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.entity.SysOperateLog;
import com.dingxin.service.SysOperateLogService;

/**
 * RabbitMq监听队列
 *
 * @author xh
 * @date 2018年6月27日 下午5:04:43 
 *
 */
@Component
public class RabbitMQReiver {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SysOperateLogService sysOperateLogService;
	
	/**
	 * 监听日志队列且处理
	 * @param sysOperateLogVo
	 */
	@RabbitListener(queues = CommonConstant.LOGQUEUE)
	@RabbitHandler
	public void receiveLog(SysOperateLogVo logVo) {
		try {
			SysOperateLog sysOperateLog = new SysOperateLog();
			BeanUtil.copyPropertiesIgnoreNull(logVo, sysOperateLog);
			sysOperateLogService.save(sysOperateLog);
		} catch (Exception e ) {
			logger.error(e.getMessage());
		}
	}
	

}
