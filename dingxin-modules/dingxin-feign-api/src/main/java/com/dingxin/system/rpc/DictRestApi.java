package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.common.util.ResultObject;
import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.DictRestApiFallBack;
import com.dingxin.system.vo.SysDictItemVo;

/**  
* @ClassName: DictRestApi  
* @Description:  对外提供数据字典api 
* @author luozb  
* @date 2018年7月6日 下午6:17:48  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
	fallback = DictRestApiFallBack.class,
	configuration = FeignConfig.class)
public interface DictRestApi{
   @RequestMapping(value = "/dict/getDictDetailByCodeAndValue", method = RequestMethod.POST)
    public  ResultObject<SysDictItemVo> getDictDetailByCodeAndValue(@RequestParam("code") String code,@RequestParam("value")String value);

   @RequestMapping(value = "/dict/getDictDetailByCode", method = RequestMethod.POST)
    public ResultObject<List<SysDictItemVo>> getDictDetailByCode(@RequestParam("code") String code);

}