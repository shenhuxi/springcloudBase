package com.dingxin.common.util;


import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * 
* Title: HttpClientUtils 
* Description:  使用spring的restTemplate替代httpclient工具
* @author dicky  
* @date 2018年6月26日 上午10:15:01
 */
public class RestTemplateUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateUtils.class);

    private static RestTemplate restTemplate = (RestTemplate) SpringUtil.getBean("restTemplate");
    /**
     * post请求
     * @param url
     * @param formParams
     * @return
     */
    public static String doPost(String url, Map<String, String> formParams) {
        if (MapUtils.isEmpty(formParams)) {
            return doPost(url);
        }
        try {
        	HttpHeaders headers = new HttpHeaders();
    		List<MediaType> accept = new LinkedList<>();
    		accept.add(MediaType.APPLICATION_JSON_UTF8);
    		headers.setAccept(accept);
            MultiValueMap<String, String> requestEntity = new LinkedMultiValueMap<>();
            formParams.keySet().stream().forEach(key -> requestEntity.add(key, MapUtils.getString(formParams, key, "")));
            return restTemplate.postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }

        return null;
    }
    
    /**
     * 自定义header的 post请求
     * @param url
     * @param paramMap
     * @param headerMap
     * @return
     */
    public static String doPost(String url, Map<String, String> paramMap,Map<String, String> headerMap) {
        if (MapUtils.isEmpty(paramMap)) {
            return doPost(url);
        }
        try {
        	HttpHeaders headers = new HttpHeaders();
    		List<MediaType> accept = new LinkedList<>();
    		accept.add(MediaType.APPLICATION_JSON_UTF8);
    		headers.setAccept(accept);
    		//设置头信息
    		headerMap.keySet().stream().forEach(key -> headers.add(key, MapUtils.getString(headerMap, key, "")));
            //设置请求参数
    		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            paramMap.keySet().stream().forEach(key -> params.add(key, MapUtils.getString(paramMap, key, "")));
            HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
            return restTemplate.postForObject(url, requestEntity, String.class);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }
        return null;
    }

    /**
     * post请求
     * @param url
     * @return
     */
    public static String doPost(String url) {
        try {
            return restTemplate.postForObject(url, HttpEntity.EMPTY, String.class);
        } catch (Exception e) {
            LOGGER.error("POST请求出错：{}", url, e);
        }

        return null;
    }

    /**
     * get请求
     * @param url
     * @return
     */
    public static String doGet(String url) {
        try {
            return restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            LOGGER.error("GET请求出错：{}", url, e);
        }
        return null;
    }

}