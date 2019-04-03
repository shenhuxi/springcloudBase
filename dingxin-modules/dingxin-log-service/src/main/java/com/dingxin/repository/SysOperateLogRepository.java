package com.dingxin.repository;

import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.entity.SysOperateLog;

/**
 * @author shixh
 */
@Repository
public interface SysOperateLogRepository extends CommonRepository<SysOperateLog,Long> {

}
