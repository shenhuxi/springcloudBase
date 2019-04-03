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
 * @update by qinzhw
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysUserControlTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	private MockMvc mockMvc;

	private static String token;
	@Autowired
	private SysUserControl sysUserControl;

	/**
	 * 初始化MockMvc
	 */
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(sysUserControl).build();
		token = "bearer " + TokenUtil.getAccessToken();
	}

	/**
	 * 用户添加 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_001_operateAdd() throws Exception {
		SysUser entity = new SysUser();
		// 设置用户基本属性
		entity.setOrgId(1L);
		entity.setName("tewt123");
		entity.setUserName("test123");
		entity.setPassWord("666666");
		entity.setMobile("13788889999");
		entity.setEmail("163@163.com");
		// 设置用户角色信息
		List<Long> roleIds = new LinkedList<Long>();
		roleIds.add(1L);
		roleIds.add(2L);
		entity.setRoleIds(roleIds);
		// 构造请求,根据uri模板和uri变量值得到一个POST请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/user/operate");
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
	 * 用户修改  测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_002_operateUpdate() throws Exception {
		SysUser entity = new SysUser();
		// 更新用户基本属性
		entity.setId(26L);
		entity.setOrgId(1L);
		entity.setName("wen13669");
		entity.setUserName("wen13669");
		entity.setEmail("177@163.com");
		entity.setMobile("18298998822");
		// 跟新用户角色信息
		List<Long> roleIds = new LinkedList<Long>();
		roleIds.add(1L);
		roleIds.add(2L);
		entity.setRoleIds(roleIds);

		// 构造请求,根据uri模板和uri变量值得到一个PUT请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/user/operate");
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
	 * 用户获取 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_003_operateGet() throws Exception {
		// 构造请求,根据uri模板和uri变量值得到一个Get请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/user/operate/26");
		MvcResult mvcResult = mockMvc
				.perform(request.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 用户删除 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_004_operateDelete() throws Exception {
		// 构造请求,根据uri模板和uri变量值得到一个Delete请求方式的MockHttpServletRequestBuilder
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/user/operate/26");
		MvcResult mvcResult = mockMvc.perform(request
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 根据用户ID获取角色 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_005_operateRole() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/operateRole/1")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 根据用户ID获取机构 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_006_findOrgByUserId() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/findOrgByUserId/1")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	@Test
	public void test_006_findOrgByUserId_fail() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/findOrgByUserId/0")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	@Test
	public void test_006_findOrgByUserId_fail2() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/findOrgByUserId/-1")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 为用户修改机构 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_007_userOrganBind() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/userOrganBind").param("userId", "1")
				.param("sysOrganIds", "8,9,15")
				.header("Authorization", token))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 启用用户 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_008_toUse() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/toUse")
				.param("id", "26")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 停用用户 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_009_toStop() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/toStop")
				.param("id", "26")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 销毁用户 测试
	 *
	 * @throws Exception
	 */
	@Test
	public void test_010_toDestroy() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/toDestroy")
				.param("id", "1")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 用户列表查询
	 *
	 * @throws Exception
	 */
	@Test
	public void test_011_list() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/list")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 登录名查询用户
	 * @throws Exception
	 */
	@Test
	public void test_012_findByUserName() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/findByUserName/admin")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 手机号码查询用户
	 * @throws Exception
	 */
	@Test
	public void test_013_findByMobile() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/findByMobile/18298998822")
				.param("id", "26")
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}






}
