package com.dingxin.file.exceptions;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

import com.dingxin.common.util.ResultObject;

/**
 * 拦截tomcate的MultipartException改称自定义异常
 * @author shixh
 *
 */
@ControllerAdvice
public class TomcateExceptionHandler {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({MultipartException.class})
	@ResponseBody
	public ResultObject handleMultipartException(HttpServletRequest request, Throwable ex) {
		String message = "";
		MultipartException mEx = (MultipartException) ex;
		Throwable cause = ex.getCause().getCause();
		if (cause instanceof SizeLimitExceededException) {
			SizeLimitExceededException flEx = (SizeLimitExceededException) cause;
			float permittedSize = flEx.getPermittedSize() / 1024 / 1024;
			message = "上传文件最大不能超过 " + permittedSize + "MB";
		} else if (cause instanceof FileSizeLimitExceededException) {
			FileSizeLimitExceededException flEx = (FileSizeLimitExceededException) mEx.getCause().getCause();
			float permittedSize = flEx.getPermittedSize() / 1024 / 1024;
			message = "上传文件最大不能超过 " + permittedSize + "MB";
		} else {
			message = "请联系系统管理员: " + ex.getMessage();
		}
		return ResultObject.fail(message);
	}
	
}
