package com.dingxin.system.controller;

import com.dingxin.common.util.TokenUtil;
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

/**
 * 描述: 系统管理-权限管理 test
 * 作者: qinzhw
 * 创建时间: 2018/7/6 10:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysPermissionControlTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockMvc mockMvc;

	private static String token;
	@Autowired
	private SysPermissionControl permissionControl;

	/**
	 * 初始化MockMvc
	 */
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(permissionControl).build();
		token = "bearer " + TokenUtil.getAccessToken();
	}

	/**
	 * 权限新增
	 * @throws Exception
	 */
	@Test
	public void test_001_operateAdd() throws Exception {
		String perJson = "{\n" +
				"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
				"                \"name\": \"身份角色设置-test\",\n" +
				"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
				"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
				"                \"method\": \"POST\",\n" +
				"                \"permissionType\": \"button\",\n" +
				"                \"parentName\": \"身份管理\",\n" +
				"                \"parentId\": \"101\",\n" +
				"                \"sortNo\": \"3\"\n" +
				"            }";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(perJson)
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	@Test
	public void test_001_operateAdd_fail() throws Exception {
		String perJson = "{\n" +
				"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
				"                \"name\": \"身份角色设置-test\",\n" +
				"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
				"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
				"                \"method\": \"POST\",\n" +
				"                \"permissionType\": \"button\",\n" +
				"                \"parentName\": \"身份管理\",\n" +
				"                \"parentId\": \"101\",\n" +
				"                \"sortNo\": \"3\"\n" +
				"            }";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(perJson)
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 权限修改
	 * @throws Exception
	 */
	@Test
	public void test_002_operateUpdate() throws Exception {
		String perJson = "{\n" +
				"                \"id\": \"102\",\n" +
				"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
				"                \"name\": \"身份角色设置-test\",\n" +
				"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
				"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
				"                \"method\": \"POST\",\n" +
				"                \"permissionType\": \"button\",\n" +
				"                \"parentName\": \"身份管理\",\n" +
				"                \"parentId\": \"101\",\n" +
				"                \"sortNo\": \"3\"\n" +
				"            }";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/permission/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(perJson)
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	@Test
	public void test_002_operateUpdate_fail() throws Exception {
		String perJson = "{\n" +
				"                \"id\": \"102\",\n" +
				"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
				"                \"name\": \"鼎信公共支撑平台\",\n" +
				"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
				"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
				"                \"method\": \"POST\",\n" +
				"                \"permissionType\": \"button\",\n" +
				"                \"parentName\": \"身份管理\",\n" +
				"                \"parentId\": \"101\",\n" +
				"                \"sortNo\": \"3\"\n" +
				"            }";
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/permission/operate");
		MvcResult mvcResult = mockMvc.perform(request
				// 设置ContentType
				.contentType(MediaType.APPLICATION_JSON)
				// 设置传值
				.content(perJson)
				.header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
	/**
	 * 权限删除
	 * @throws Exception
	 */
	@Test
	public void test_003_operateDelete() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/permission/operate/200,201");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			Assert.assertEquals("请求错误", 200, status);
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 权限查询
	 * @throws Exception
	 */
	@Test
	public void test_004_operateList() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/list");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			Assert.assertEquals("请求错误", 200, status);
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 权限查询
	 * @throws Exception
	 */
	@Test
	public void test_005_search() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/permission/search/1");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			Assert.assertEquals("请求错误", 200, status);
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 权限查询
	 * @throws Exception
	 */
	@Test
	public void test_006_searchAllTree() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/permission/searchAllTree");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			Assert.assertEquals("请求错误", 200, status);
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取前端菜单树
	 * @throws Exception
	 */
	@Test
	public void test_007_searchMenuTree() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/searchMenuTree");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
			Assert.assertEquals("请求错误", 200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 权限树查询(此方法适用于orcal或dm)
	 * @throws Exception
	 */
	@Test
	public void test_008_searchAll() {
//		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/permission/searchAll/1");
//		MvcResult mvcResult = null;
//		try {
//			mvcResult = mockMvc.perform(request
//                    .header("Authorization", token)).andReturn();
//			int status = mvcResult.getResponse().getStatus();
//			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
//			Assert.assertEquals("请求错误", 200, status);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	/**
	 * 权限生成数据保存
	 * @throws Exception
	 */
	@Test
	public void test_009_gen() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/gen");
		MvcResult mvcResult = null;
		try {
			String perJson = "[{\n" +
					"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
					"                \"name\": \"身份角色设置-test\",\n" +
					"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
					"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
					"                \"method\": \"POST\",\n" +
					"                \"permissionType\": \"button\",\n" +
					"                \"parentName\": \"身份管理\",\n" +
					"                \"parentId\": \"101\",\n" +
					"                \"sortNo\": \"3\"\n" +
					"            }]";
			mvcResult = mockMvc.perform(request
					.contentType(MediaType.APPLICATION_JSON)
					// 设置传值
					.param("perjson",perJson)
					.param("appName","system")
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
			Assert.assertEquals("请求错误", 200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test_009_gen_fail() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/permission/gen");
		MvcResult mvcResult = null;
		try {
			String perJson = "{\n" +
					"                \"code\": \"_system_userorganRole_identRolesBind_POST\",\n" +
					"                \"name\": \"身份角色设置-test\",\n" +
					"                \"description\": \"系统管理-身份管理-身份角色设置\",\n" +
					"                \"url\": \"/system/userorganRole/identRolesBind\",\n" +
					"                \"method\": \"POST\",\n" +
					"                \"permissionType\": \"button\",\n" +
					"                \"parentName\": \"身份管理\",\n" +
					"                \"parentId\": \"101\",\n" +
					"                \"sortNo\": \"3\",,,\n" +
					"            }]";
			mvcResult = mockMvc.perform(request
					.contentType(MediaType.APPLICATION_JSON)
					// 设置传值
					.param("perjson",perJson)
					.param("appName","system")
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
			Assert.assertEquals("请求错误", 200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询用户权限
	 * @throws Exception
	 */
	@Test
	public void test_010_findRoles() {
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/permission/findRoles/1");
		MvcResult mvcResult = null;
		try {
			mvcResult = mockMvc.perform(request
                    .header("Authorization", token)).andReturn();
			int status = mvcResult.getResponse().getStatus();
			logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
			Assert.assertEquals("请求错误", 200, status);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
