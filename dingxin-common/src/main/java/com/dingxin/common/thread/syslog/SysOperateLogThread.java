/**
 * 
 */
package com.dingxin.common.thread.syslog;

import com.dingxin.common.rabbitmq.RabbitMQSender;
import com.dingxin.common.util.SpringUtil;
import com.dingxin.common.vo.log.SysOperateLogVo;

/**  
* Title: SysOperateLogThread 
* Description:  系统日志线程类
* @author dicky  
* @date 2018年6月30日 下午4:13:13  
*/

public class SysOperateLogThread implements Runnable{

	private SysOperateLogVo sysOperateLogVo;//系统操作日志
	
	public SysOperateLogThread (SysOperateLogVo sysOperateLogVo) {
		this.sysOperateLogVo = sysOperateLogVo;
	}
	@Override
	public void run() {
		RabbitMQSender rabbitMQSender = SpringUtil.getBean(RabbitMQSender.class);
		rabbitMQSender.sender(sysOperateLogVo);//将日志内容发送到MQ队列
	}
	public SysOperateLogVo getSysOperateLogVo() {
		return sysOperateLogVo;
	}
	public void setSysOperateLogVo(SysOperateLogVo sysOperateLogVo) {
		this.sysOperateLogVo = sysOperateLogVo;
	}
	
	
}
