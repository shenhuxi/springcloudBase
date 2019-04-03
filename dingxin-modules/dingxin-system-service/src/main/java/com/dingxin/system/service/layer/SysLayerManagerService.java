package com.dingxin.system.service.layer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysLayerManager;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.repository.layer.SysLayerManagerRepository;

/**
 * 分级管理员 service
 * 
 * @author luozb
 */
@Service
@Transactional(rollbackFor = RestException.class)
public class SysLayerManagerService extends CommonService<SysLayerManager, Long> {

	@Autowired
	private SysLayerManagerRepository sysLayerManagerRepository;

	

	@Override
	public CommonRepository<SysLayerManager, Long> getCommonRepository() {
		return sysLayerManagerRepository;
	}

	/**
	 * 保存,判断重复名字,当新增的机构有父级编号时，找到该父级机构，并将其isparent字段设置为0（是）
	 * 
	 * @param sysOrgan
	 * @return
	 */
	public ResultObject checkAndSave(SysLayerManager entity) {
		SysLayerManager sysLayer = sysLayerManagerRepository.findBySysOrganIdAndSysUserId(entity.getSysOrganId(), entity.getSysUserId());
		if (sysLayer!=null) {
			return ResultObject.fail("请不要添加重复数据！");
		}
		entity = sysLayerManagerRepository.save(entity);
		return ResultObject.ok("操作成功!", entity);
	}
	
	public ResultObject checkAndUpdate(SysLayerManager entity) {
		SysLayerManager slm = sysLayerManagerRepository.findById(entity.getId());
		if (slm!=null) {
			entity = sysLayerManagerRepository.saveAndFlush(entity);
			return ResultObject.ok("修改成功!", entity);
		}
		return ResultObject.fail("您修改的对象不存在！");
	}

	public ResultObject deleteByIds(String ids) {
		String[] strArr = ids.split(",");
		List<String> list = Arrays.asList(strArr);
		List<Long> longList = new ArrayList<>();
		for (String str : list) {
			long i = Long.parseLong(str);
			longList.add(i);
		}
		sysLayerManagerRepository.deleteByIdIn(longList);
		return ResultObject.ok("删除成功!");
	}

	public SysLayerManager findBySysOrganIdAndSysUserId(Long curOrgId, Long curUserId) {
		return sysLayerManagerRepository.findBySysOrganIdAndSysUserId(curOrgId,curUserId);
	}


	
}
