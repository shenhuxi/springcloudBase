package com.dingxin.system.controller;

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

import com.alibaba.fastjson.JSONObject;
import com.dingxin.common.util.TokenUtil;
import com.dingxin.system.entity.SysRole;

/**
 * 系统管理-角色角色 Test
 *
 * @author luozb
 * @date 2018年7月6日 下午5:27:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysRoleControlTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private MockMvc mockMvc;

	private static String token;
	@Autowired
	private SysRoleControl sysRoleControl;

	/**
	 * 初始化MockMvc
	 */
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(sysRoleControl).build();
		token = "bearer " + TokenUtil.getAccessToken();
	}

	/**
	 * 角色保存 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_001_operateSave() throws Exception {
		SysRole entity = new SysRole(); 
		// 设置角色基本属性
		entity.setName("广州供电局市级管理员");
		entity.setCode("GZcity_manager");
		// 构造请求,根据uri模板和uri变量值得到一个POST请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/role/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(JSONObject.toJSONString(entity))
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 角色修改  测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_002_operateUpdate() throws Exception {
		SysRole entity = new SysRole();
		// 更新角色基本属性
		entity.setId(2L);
		entity.setName("超级管理员");
		entity.setCode("super_manager");
		entity.setDescription("超级管理描述");
		// 构造请求,根据uri模板和uri变量值得到一个PUT请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/role/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(JSONObject.toJSONString(entity))
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	

	/**
	 * 角色删除 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_004_operateDelete() throws Exception {
		// 构造请求,根据uri模板和uri变量值得到一个Delete请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/role/operate/6");
		MvcResult mvcResult = mockMvc.perform(request
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 根据角色ID获取权限 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_005_findPerByRoleId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/role/findPerByRoleId/10")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	
	/**
	 * 权限设置 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_006_rolePerBind() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/role/rolePerBind").param("roleId", "10")
				.param("sysPermissionIds", "108,109,107,111,112,123")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	
	
	/**
	 * 查询角色列表(带分页) 测试  
	 *
	 * @throws Exception
	 */
	@Test
	public void test_007_listGet() throws Exception {
		// 构造请求,根据uri模板和uri变量值得到一个Get请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/role/list");
		MvcResult mvcResult = mockMvc
				.perform(request.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	
	
	/**
	 * 不带分页但按sort字段降序排列返回查询到的所有角色  测试  
	 *
	 * @throws Exception
	 */
	@Test
	public void test_008_listNoPageGet() throws Exception {
		// 构造请求,根据uri模板和uri变量值得到一个Get请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/role/listNoPage");
		MvcResult mvcResult = mockMvc
				.perform(request.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	
	/**
	 * 根据身份ID获取角色  测试
	 * 需要修改identityId
	 *
	 * @throws Exception
	 */
	@Test
	public void test_009_getIdentityRole() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/role/getIdentityRole/1")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	
	






}
