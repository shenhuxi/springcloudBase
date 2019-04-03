package com.dingxin.system.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.common.util.ResultObject;
import com.dingxin.system.rpc.DictRestApi;
import com.dingxin.system.vo.SysDictItemVo;

/**
 * 描述: feign 调用失败处理 
 * 作者: lzb 
 * 创建时间: 2018/7/04 11:16
 */
@Service
public class DictRestApiFallBack implements DictRestApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public  ResultObject<SysDictItemVo> getDictDetailByCodeAndValue(@RequestParam("code") String code,
			@RequestParam("value") String value) {
		logger.error("调用服务异常:getDictDetailByCodeAndValue(): {}", code);
		return null;
	}

	@Override
	public ResultObject<List<SysDictItemVo>> getDictDetailByCode(@RequestParam("code") String code) {
		logger.error("调用服务异常:getDictDetailByCode(): {}", code);
		return null;
	}
}