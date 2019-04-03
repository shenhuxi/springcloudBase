package com.dingxin.system.repository.layer;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysLayerManager;


@Repository
public interface SysLayerManagerRepository extends CommonRepository<SysLayerManager,Long> {

	SysLayerManager findBySysOrganIdAndSysUserId(long sysOrganId, long sysUserId);

	SysLayerManager findById(long id);

	void deleteByIdIn(List<Long> longList);

	

}
