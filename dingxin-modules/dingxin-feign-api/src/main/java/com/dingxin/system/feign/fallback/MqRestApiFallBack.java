package com.dingxin.system.feign.fallback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.system.rpc.MqRestApi;
/**
 * 调用Feign失败处理
 *
 * @author xh
 * @date 2018年7月10日 上午9:55:13 
 *
 */
@Service
public class MqRestApiFallBack implements MqRestApi{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResultObject<SysOperateLogVo> sendLog(SysOperateLogVo logVo) {
		logger.error("调用服务异常:sendLog(): {}");
		return null;
	}

}
