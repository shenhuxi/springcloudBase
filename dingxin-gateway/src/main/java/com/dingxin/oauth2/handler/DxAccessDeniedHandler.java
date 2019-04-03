package com.dingxin.oauth2.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DxAccessDeniedHandler extends OAuth2AccessDeniedHandler {
    private static Logger logger = LoggerFactory.getLogger(DxAccessDeniedHandler.class);
    
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 授权拒绝处理
     *
     * @param request       request
     * @param response      response
     * @param authException authException
     * @throws IOException      IOException
     * @throws ServletException ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException authException) throws IOException, ServletException {
        logger.error("authException,{}",authException);
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        Map<String,Object> result = new HashMap<>();
        result.put("success",false);
        result.put("code",403);
        result.put("msg", "非常抱歉，您没有访问该资源的权限！");
        response.setStatus(403);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(result));
    }
}
