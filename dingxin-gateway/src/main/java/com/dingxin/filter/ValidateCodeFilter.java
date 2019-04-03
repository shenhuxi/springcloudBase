package com.dingxin.filter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import com.dingxin.constants.ApiGateWayConstants;
import com.dingxin.constants.FilterConstants;
import com.dingxin.constants.RedisKeyConstant;
import com.dingxin.exception.ValidateCodeException;
import com.dingxin.properties.DxApiGateWateProperties;
import com.dingxin.util.StringUtils;
import com.dingxin.util.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import net.sf.json.JSONObject;

@RefreshScope
@Configuration("validateCodeFilter")
@ConditionalOnProperty(value = ApiGateWayConstants.API_CONDITION_VALIDATE_CODE, havingValue = "true")
public class ValidateCodeFilter extends ZuulFilter {
    
	public final Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String EXPIRED_CAPTCHA_ERROR = "验证码已过期，请重新获取";

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private DxApiGateWateProperties dxApiGateWateProperties;
	
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    
    @Override
    public int filterOrder() {
        return FilterConstants.SEND_ERROR_FILTER_ORDER + 1;
    }

    /**
     * 是否校验验证码
     *  判断验证码开关是否开启
     *
     * @return true/false
     */
    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String url = request.getRequestURI().toString();
        url = url.replaceFirst(ApiGateWayConstants.API_PREFIX_URI, "");//切除网关的路径前缀
		if(dxApiGateWateProperties != null) {
			Set<String> urlSet = dxApiGateWateProperties.getCode().getValidateUrls();
			if (!urlSet.contains(url)) {
	            return false;
	        }
        }
        return true;
    }

    @Override
    public Object run() {
        try {
            checkCode(RequestContext.getCurrentContext().getRequest());
        } catch (Exception e) {
        	logger.error(e.getMessage());
            RequestContext ctx = RequestContext.getCurrentContext();
            Map<String,Object> result = new HashMap<>();
            result.put("success",false);
            result.put("code",400);
            result.put("msg", e.getMessage());
            ctx.setResponseStatusCode(478);
            ctx.setSendZuulResponse(false);
            ctx.getResponse().setContentType("application/json;charset=UTF-8");
            ctx.setResponseBody(JSONObject.fromObject(result).toString());
        }
        return null;
    }

    /**
     * 检查code
     *
     * @param httpServletRequest request
     * @throws Exception 
     */
    private void checkCode(HttpServletRequest request) throws Exception {
        String code = request.getParameter("authCode");
        if (StringUtils.isBlank(code)) {
            throw new ValidateCodeException("请输入验证码！");
        }
		String key = WebUtils.getRequestIp(request) + RedisKeyConstant.USER_AUTHCODE_KEY;
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object codeObj = valueOperations.get(key);
        if (codeObj == null || StringUtils.isBlank(codeObj.toString())) {
            throw new ValidateCodeException(EXPIRED_CAPTCHA_ERROR);
        }
        String saveCode = "";
        if(JSONObject.fromObject(codeObj) != null) {
        	saveCode = JSONObject.fromObject(codeObj).get("value").toString();
        }
        if (!StringUtils.equalsIgnoreCase(saveCode, code)) {
            throw new ValidateCodeException("验证码错误，请重新输入！");
        }
    }
    
}