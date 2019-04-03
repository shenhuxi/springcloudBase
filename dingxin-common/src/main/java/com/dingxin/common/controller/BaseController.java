package com.dingxin.common.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.alibaba.fastjson.JSON;
import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.Oauth2Constant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.vo.system.SysUserVo;

/**
 * common controller
 * 
 * @author shixh
 */
public class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final String USER_NULL_MSG = "用户没有登陆,请重新登陆!";

	@Autowired
	public HttpServletRequest request;

	@Resource
	public RedisTemplate<String, Object> redisTemplate;

	/**
	 * getHeadersInfo
	 * 
	 * @author shixh
	 * @return
	 */
	public Map<String, String> getHeadersInfo() {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * getLoginUser
	 * 
	 * @author shixh
	 * @return
	 * @throws Exception
	 */
	public SysUserVo getLoginUser() throws Exception {
		ValueOperations<String, Object> operations = redisTemplate.opsForValue();
		String jsonUser = (String) operations.get(this.getUserToken() + RedisKeyConstant.USER_KEY);
		SysUserVo user = JSON.parseObject(jsonUser, SysUserVo.class);
		if (user == null) {
			throw new RestException(USER_NULL_MSG);
		}
		return user;
	}

	/**
	 * getUserId
	 * 
	 * @author shixh
	 * @return
	 * @throws Exception
	 */
	public long getLoginUserId() throws Exception {
		return getLoginUser().getId();
	}

	/**
	 * getLoginUserName
	 * 
	 * @author shixh
	 * @return
	 * @throws Exception
	 */
	public String getLoginUserName() throws Exception {
		return getLoginUser().getUserName();
	}

	/**
	 * get oauth2 authorization token
	 * 
	 * @author shixh
	 * @return
	 */
	public String getUserToken() {
		String authorization = request.getHeader(Oauth2Constant.REQ_HEADER);
		authorization = authorization.replace(Oauth2Constant.TOKEN_SPLIT, "");
		return authorization;
	}

	/**
	 * 封装分页信息
	 * 
	 * @author shixh
	 * @param pageNumber
	 * @param pagzSize
	 * @param sort
	 * @return
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, Sort sort) {
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 封装分页信息
	 * 
	 * @author shixh
	 * @param pageNumber
	 * @param pagzSize
	 * @return
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize) {
		return new PageRequest(pageNumber - 1, pagzSize);
	}

	/**
	 * 封装分页信息
	 * 
	 * @author shixh
	 * @param pageNumber
	 * @param pagzSize
	 * @param sort
	 * @param sortType
	 * @return
	 */
	public PageRequest buildPageRequest(int pageNumber, int pagzSize, String sort, String sortType) {
		Sort s;
		if (StringUtils.isBlank(sort) || StringUtils.isBlank(sortType)) {
			s = new Sort(Direction.ASC, "id");
		} else {
			s = new Sort(Direction.fromString(sortType), sort);
		}
		return new PageRequest(pageNumber - 1, pagzSize, s);
	}

	/**
	 * 封装分页信息
	 * 
	 * @author shixh
	 * @param searchParams
	 * @return
	 */
	public PageRequest buildPageRequest(Map<String, Object> searchParams) {
		String pageNum = (String) searchParams.get(CommonConstant.PAGE_PAGENUM);
		String pageSize = (String) searchParams.get(CommonConstant.PAGE_PAGESIZE);
		String sortType = (String) searchParams.get(CommonConstant.PAGE_SORTTYPE);
		String sort = (String) searchParams.get(CommonConstant.PAGE_SORT);
		if (StringUtils.isBlank(pageNum)) {
			pageNum = CommonConstant.PAGE_1;
		}
		if (StringUtils.isBlank(pageSize)) {
			pageSize = CommonConstant.PAGE_10;
		}
		searchParams.remove(CommonConstant.PAGE_PAGENUM);
		searchParams.remove(CommonConstant.PAGE_PAGESIZE);
		searchParams.remove(CommonConstant.PAGE_SORTTYPE);
		searchParams.remove(CommonConstant.PAGE_SORT);
		return buildPageRequest(Integer.parseInt(pageNum), Integer.parseInt(pageSize), sort, sortType);
	}




	/**
	 * 请求参数转MAP
	 * 
	 * @author shixh
	 * @return
	 */
	public Map<String, Object> getParameterMap() {
		Map<String,String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<>();
		Iterator<Map.Entry<String,String[]>> entries = properties.entrySet().iterator();
		Map.Entry<String,String[]> entry;
		String name = "";
		Object value = "";
		while (entries.hasNext()) {
			entry = entries.next();
			name = entry.getKey();
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String valueStr = "";
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					valueStr = values[i] + ",";
				}
				value = valueStr.substring(0, valueStr.length() - 1);
			} else {
				value = valueObj;
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

	/**
	 * 描述: 获取验证后的错误信息 作者: qinzhw
	 */
	public String getValidErrorMsg(BindingResult br) {
		List<ObjectError> errors = br.getAllErrors();
		List<String> msglist = new ArrayList<>();
		errors.forEach(error -> msglist.add(error.getDefaultMessage()));
		return StringUtils.join(msglist, "|");
	}

}
