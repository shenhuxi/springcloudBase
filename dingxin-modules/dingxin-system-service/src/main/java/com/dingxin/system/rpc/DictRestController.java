package com.dingxin.system.rpc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.system.entity.SysDictItem;
import com.dingxin.system.repository.dictionary.SysDictItemRepository;
import com.dingxin.system.service.dictionary.SysDictItemService;
import com.dingxin.system.vo.SysDictItemVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 描述: 对外提供数据字典api
 * 作者: qinzhw
 * 创建时间: 2018/6/28 9:37
 */
@RestController
@RequestMapping("/dict")
@Api(tags = "对外数据字典API")
public class DictRestController extends BaseController implements DictRestApi{

    @Autowired
    private SysDictItemService itemService;
    @Autowired
    private SysDictItemRepository sysDictItemRepository;

    @RequestMapping(value = "/getDictDetailByCodeAndValue", method = RequestMethod.POST)
    @ApiOperation(value = "获取字典细项", notes = "根据code和value去获取具体的字典细项")
    public  ResultObject<SysDictItemVo> getDictDetailByCodeAndValue(
            @ApiParam(name = "code",value="字典code",required = true)      @RequestParam("code") String code,
            @ApiParam(name = "value", value="字典value",required = true)   @RequestParam("value")String value) {
    	
    	SysDictItem sdi= sysDictItemRepository.findByOptionValueAndGroupCodeAndDeleteState(value, code, CommonConstant.NORMAL);
    	
    	return ResultObject.ok(BeanUtil.copyProperties(sdi,new SysDictItemVo()));
    }

    @Override
    @RequestMapping(value = "/getDictDetailByCode", method = RequestMethod.POST)
    @ApiOperation(value = "获取字典细项列表", notes = "根据字典code获取字典细项列表")
    public ResultObject<List<SysDictItemVo>> getDictDetailByCode(
            @ApiParam(name = "code", value = "字典code", required = true) @RequestParam("code") String code){
    	List<SysDictItemVo> list=new ArrayList<>();
    	List<SysDictItem> listsrc=itemService.findByGroupCodeAndDeleteState(code, CommonConstant.NORMAL);
    	for(SysDictItem sdi:listsrc) {
    		list.add(BeanUtil.copyProperties(sdi,new SysDictItemVo()));
    	}
        return ResultObject.ok(list);
    }

}