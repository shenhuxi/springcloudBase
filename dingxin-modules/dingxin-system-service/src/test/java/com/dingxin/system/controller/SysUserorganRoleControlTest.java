package com.dingxin.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.dingxin.common.util.TokenUtil;
import com.dingxin.system.entity.SysUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.LinkedList;
import java.util.List;

/**
 * 系统管理-用户管理 Test
 *
 * @author xh
 * @date 2018年6月13日 下午5:27:02
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysUserorganRoleControlTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private MockMvc mockMvc;

	private static String token;
	@Autowired
	private SysUserorganRoleControl sysUserorganRoleControl;

	/**
	 * 初始化MockMvc
	 */
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(sysUserorganRoleControl).build();
		token = "bearer " + TokenUtil.getAccessToken();
	}
	/**
	 * 描述: 身份角色设置
	 * 作者: qinzhw
	 * 创建时间: 2018/7/5 17:31
	 * @throws Exception
	 */
	@Test
	public void test_001_identRolesBind() throws Exception {

		// 构造请求,根据uri模板和uri变量值得到一个POST请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/userorganRole/identRolesBind");
		MvcResult mvcResult = mockMvc.perform(request
				.param("userorganId","64")
				.param("roleIds","6,7")
				// 设置传值
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	@Test
	public void test_001_identRolesBind_fail() throws Exception {

		// 构造请求,根据uri模板和uri变量值得到一个POST请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/userorganRole/identRolesBind");
		MvcResult mvcResult = mockMvc.perform(request
				.param("userorganId","-64")
				.param("roleIds","6,7")
				// 设置传值
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
}
