package com.dingxin.common.hander;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.ResultObject;

/**
 * 接口异常统一处理
 * 抛出exception执行exceptionHandler,
 * 自定义异常信息可以单独添加
 * @author shixh
 */
@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@ExceptionHandler	
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject runtimeExceptionHandler(RuntimeException e) {
		logger.error("runtimeExceptionHandler error!", e);
		return ResultObject.fail(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject illegalParamsExceptionHandler(MethodArgumentNotValidException e) {
		logger.error("illegalParamsExceptionHandler error!", e);
		return ResultObject.fail(e.getMessage());
	}
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject nullPointerExceptionExceptionHandler(NullPointerException e) {
		logger.error("nullPointerExceptionExceptionHandler error!", e);
		return ResultObject.fail("空指针异常");
	}
	
	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject accessDeniedExceptionHandler(AccessDeniedException e) {
		logger.error("nullPointerExceptionExceptionHandler error!", e);
		return ResultObject.forbidden();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject exceptionHandler(Exception e) {
		logger.error("exceptionHandler error!", e);
		return ResultObject.fail("操作失败");
	}
	
	@ExceptionHandler(RestException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	private ResultObject restExceptionHandler(RestException e) {
		return ResultObject.fail(e.getMessage());
	}
}
