package com.dingxin.system.controller;

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
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSONObject;
import com.dingxin.common.util.TokenUtil;
import com.dingxin.system.entity.SysOrgan;
/**
 * SysOrganController测试类
 *
 * @author xh
 * @date 2018年7月6日 下午2:21:14 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysOrganControllerTest {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockMvc mockMvc;

	private String token;
	
	@Autowired
	private SysOrganControl sysOrganControl;
	
	 /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sysOrganControl).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }
    
    /**
     * 机构新增测试
     * @throws Exception
     */
    @Test
    public void test_001_saveOrgan() throws Exception {
    	SysOrgan sysOrgan = new SysOrgan();
    	sysOrgan.setOrgName("testOrgName");
    	sysOrgan.setOrgCode("testOrgCode");
    	sysOrgan.setParentId(1L);
    	sysOrgan.setOrgLevel(1);
    	sysOrgan.setEntLevel("testEntLevel");
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/org/save");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysOrgan))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 机构更新测试 1
     * @throws Exception
     */
    @Test
    public void test_002_updateOrgan() throws Exception {
    	SysOrgan sysOrgan = new SysOrgan();
    	sysOrgan.setId(1L);
    	sysOrgan.setOrgName("testOrgName");
    	sysOrgan.setOrgCode("testOrgCode");
    	sysOrgan.setParentId(1L);
    	sysOrgan.setOrgLevel(1);
    	sysOrgan.setEntLevel("testEntLevel");
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/org/update");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysOrgan))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 机构更新测试 2
     * @throws Exception
     */
    @Test
    public void test_003_updateOrgan() throws Exception {
    	SysOrgan sysOrgan = new SysOrgan();
    	sysOrgan.setOrgName("testOrgName");
    	sysOrgan.setOrgCode("testOrgCode");
    	sysOrgan.setParentId(1L);
    	sysOrgan.setOrgLevel(1);
    	sysOrgan.setEntLevel("testEntLevel");
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/org/update");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysOrgan))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 机构删除测试
     * @throws Exception
     */
    @Test
    public void test_004_deleteOrgan() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/org/delete/5,6");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 机构查询
     * @throws Exception
     */
    @Test
    public void test_005_listOrgan() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/org/list");
    	MvcResult mvcResult = mockMvc.perform(request
    			.param("EQ_orgName", "testOrgName")
    			.param("EQ_orgCode", "testOrgCode")
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 据父节点查询 动态机构树查询（每点击一次上级按钮，弹出下属机构）
     * @throws Exception
     */
    @Test
    public void test_006_searchParentOrgan() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/org/search/1");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 静态机构树查询
     * @throws Exception
     */
    @Test
    public void test_007_staticTree() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/org/staticTree");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 静态机构树查询
     * @throws Exception
     */
    @Test
    public void test_008_findById() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/org/findById/1");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 机构树查询
     * @throws Exception
     */
    @Test
    public void test_009_searchAll() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/org/searchAll/1");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
}
