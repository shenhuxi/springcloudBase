package com.dingxin.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dingxin.model.ResultObject;
import com.dingxin.util.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component 
public class ResponseStatusCodeZuulFilter extends ZuulFilter{
	
	public final Logger logger = LoggerFactory.getLogger(getClass());
	public static final String DEFAULT_ERR_MSG = "系统繁忙,请稍后再试";
	private static final String ERROR_STATUS_CODE_KEY = "error.status_code";
	
	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
        return ctx.containsKey(ERROR_STATUS_CODE_KEY);
	}

	@Override
	public Object run() {       
		RequestContext requestContext = RequestContext.getCurrentContext();
		doMyError(requestContext);
        return null;
    }

	/**
	 * 自定义异常，和业务异常统一格式
	 * @author shixh
	 * @param requestContext
	 */
	public void doMyError(RequestContext requestContext) {
		try {
			HttpServletRequest request = requestContext.getRequest();
			int statusCode = (Integer) requestContext.get(ERROR_STATUS_CODE_KEY);
			String message = (String) requestContext.get("error.message");
			String data = "";
            if (requestContext.containsKey("error.exception")) {
            	Throwable e = (Exception) requestContext.get("error.exception");
                Throwable re = getOriginException(e);
                data = re.getMessage();
                if(re instanceof java.net.ConnectException){
                    message = "连接拒绝,请稍后尝试.";
                    logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }else if(re instanceof java.net.SocketTimeoutException){
                    message = "连接超时,请稍后尝试.";
                    logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }else if(re instanceof com.netflix.client.ClientException){
                    message = "连接异常,请稍后尝试.";
                    logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }/*else if(re instanceof java.net.SocketException){
                	message = "连接没响应 ,请稍后尝试.";
                	logger.warn("uri:{},error:{}" ,request.getRequestURI(),re.getMessage());
                }*/else {	
                	logger.warn("Error during filtering",e);
                }
            }
            if(StringUtils.isBlank(message)) {
                message = DEFAULT_ERR_MSG;
            }
	            request.setAttribute("javax.servlet.error.status_code", statusCode);
	            request.setAttribute("javax.servlet.error.message", message);
	            WebUtils.responseToJson(requestContext.getResponse(), ResultObject.fail(message,data));
		} catch (Exception e) {
	            String error = "Error during filtering[ErrorFilter]";
	            logger.error(error,e);
	            WebUtils.responseToJson(requestContext.getResponse(), ResultObject.fail(error,e.getMessage()));
		}
	}
	
	private Throwable getOriginException(Throwable e){
        e = e.getCause();
        while(e.getCause() != null){
            e = e.getCause();
        }
        return e;
    }
   
   
	@Override
	public String filterType() {
		return "post"; // 设置过滤器类型为：post
	}

	@Override
	public int filterOrder() {
		return 0;// 设置执行顺序
	}

}
