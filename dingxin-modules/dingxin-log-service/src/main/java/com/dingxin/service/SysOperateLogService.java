package com.dingxin.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.ClientUtil;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.entity.SysOperateLog;
import com.dingxin.repository.SysOperateLogRepository;

/**
 * @author shixh
 */
@Service
@Transactional(rollbackFor = RestException.class)
public class SysOperateLogService extends CommonService<SysOperateLog,Long>{
	
	@Autowired
	private SysOperateLogRepository sysOperateLogRepository;

	@Override
	public CommonRepository<SysOperateLog, Long> getCommonRepository() {
		return sysOperateLogRepository;
	}

	public void saveOperateLog(String business, SysUserVo sysUser,String operate,String operateContent, HttpServletRequest request) {
		SysOperateLog sysOperateLog = new SysOperateLog();
		sysOperateLog.setIp(ClientUtil.getRequestIp(request));
		sysOperateLog.setOperateBusiness(business);
		sysOperateLog.setUserId(sysUser.getId());
		sysOperateLog.setUserName(sysUser.getUserName());
		sysOperateLog.setUrl(request.getRequestURL().toString());
		sysOperateLog.setOperateName(operate);
		sysOperateLog.setOperateContent(operateContent);
		sysOperateLogRepository.save(sysOperateLog);
	}
	
 
}


