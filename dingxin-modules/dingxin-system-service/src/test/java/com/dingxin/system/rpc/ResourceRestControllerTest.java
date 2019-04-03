/**
 * 
 */
package com.dingxin.system.rpc;

import java.io.IOException;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dingxin.common.util.TokenUtil;


/**
 * Title: ResourceRestControllerTest 
 * Description: 权限资源单元测试
 * @author dicky
 * @date 2018年6月30日 下午4:52:51
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceRestControllerTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockMvc mockMvc;

	private static String token;
	@Autowired
	private ResourceRestController resourceRestController;

	/**
	 * 初始化MockMvc
	 */
	@Before
	public void before() throws IOException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(resourceRestController).build();
		token = "bearer " + TokenUtil.getAccessToken();
	}

	/**
	 * 根据用户ID查询用户权限
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGetPermissionsByUserId_001() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/resource/getPermissionsByUserId")
				.param("userId", "3").header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}

	/**
	 * 根据用户ID查询用户权限
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGindPermissionsByIdentId_002() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/resource/getPermissionsByIdentId")
				.param("identId", "54").header("Authorization", token)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
		Assert.assertEquals("请求错误", 200, status);
	}
}
