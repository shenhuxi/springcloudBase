package com.dingxin.common.aspect;


import java.util.concurrent.ExecutorService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.Oauth2Constant;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.thread.syslog.SysOperateLogThread;
import com.dingxin.common.util.ClientUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.common.vo.system.SysUserVo;

/**
 * 拦截@UserOperate注解的方法
 * 将操作日志保存到数据库(后期可以用NOSQL)
 * @author shixh
 */
@Aspect
@Component
public class SysOperateLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(SysOperateLogAspect.class);

    private ThreadLocal<SysOperateLogVo> operateLogThreadLocal = new ThreadLocal<>();

	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	public ExecutorService executorService;
    
    private long startTime = 0;
    
	public SysUserVo getLoginUser(HttpServletRequest request) throws Exception {
		String authorization = request.getHeader(Oauth2Constant.REQ_HEADER);
		if(StringUtils.isNoneBlank(authorization)) {
			authorization = authorization.replace(Oauth2Constant.TOKEN_SPLIT,"");
			ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
			String json_User = (String)valueOperations.get(authorization+RedisKeyConstant.USER_KEY);
			SysUserVo user = JSON.parseObject(json_User,SysUserVo.class);
			if(user==null) {
				//获取验证码和登陆接口
				user = new SysUserVo("游客登陆",0);
			}
			return user;
		}
		return new SysUserVo("游客登陆",0);
		
	}

	
    @Before("@annotation(userOperate)")
    public void doBefore(JoinPoint joinPoint, UserOperate userOperate) throws Exception{
    	startTime = System.currentTimeMillis();
    	if(joinPoint.getArgs().length>0) {
    		logger.info("Args: {}",joinPoint.getArgs()[0].toString());
    	}
    	SysOperateLogVo operateLog = new SysOperateLogVo();
    	logger.info("userOperate: {}",userOperate.name());
    	ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SysUserVo user = getLoginUser(request);
        operateLog.setOperateName(userOperate.name());
        operateLog.setUserId(user.getId());
        operateLog.setUserName(user.getUserName());
        operateLog.setIp(ClientUtil.getRequestIp(request));
        operateLog.setUrl(request.getRequestURL().toString());
        operateLog.setOperateBusiness(userOperate.business());
        operateLogThreadLocal.set(operateLog);
    }

    @AfterReturning(pointcut = "@annotation(userOperate)",returning = "object")
    public void doAfterReturing(Object object, UserOperate userOperate) throws IllegalArgumentException, IllegalAccessException{
    	SysOperateLogVo operateLog = operateLogThreadLocal.get();
    	long endTime = System.currentTimeMillis();
    	//请求失败记录失败日志
    	if(object instanceof ResultObject) {
    		ResultObject resultObject = (ResultObject)object;
    		if(!ResultObject.OK.equals(resultObject.getCode()) || OperateConstant.DELETE.equals(userOperate.name())) {
    			operateLog.setOperateResult(JSON.toJSONString(object));
    		}
    	}
    	operateLog.setTime(endTime-startTime);
    	try {
    		executorService.submit(new SysOperateLogThread(operateLog));//使用异步线程去发送
    	} catch(Exception e) {
    		logger.error("Rabbit消息发送失败:    " + e.getMessage());
    	} finally {
    		operateLogThreadLocal.remove();
		}
    	operateLogThreadLocal.remove();
    }
 
}
