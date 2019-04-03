package com.dingxin.system.config;

import com.dingxin.common.constant.Oauth2Constant;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 描述: 设置feign 请求时request参数 如header
 * 作者: qinzhw
 * 创建时间: 2018/6/4 16:21
 */
public class FeignConfig implements RequestInterceptor {

    /**
     * token请求头名称 request 提取成小写
     */
    public static final String REQ_HEADER = "authorization";

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = getHttpServletRequest();
        if (request == null) {
            return;
        }
        String token = getHeaders(request).get(REQ_HEADER);
        if (StringUtils.isBlank(token)) {
            Object attribute = request.getAttribute(Oauth2Constant.REQ_HEADER);
            if (attribute != null) {
                token = attribute.toString();
            }
        }
        requestTemplate.header(Oauth2Constant.REQ_HEADER, token);
    }

    private HttpServletRequest getHttpServletRequest() {
        try {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    @Bean
    public FeignHystrixConcurrencyStrategy feignHystrixConcurrencyStrategy() {
        return new FeignHystrixConcurrencyStrategy();
    }    
}
