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
import com.dingxin.system.entity.SysDictItem;

/**
 * SysDictItemController 测试类
 *
 * @author xh
 * @date 2018年7月5日 下午4:32:11 
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysDictItemControllerTest {
	
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockMvc mockMvc;

	private String token;
	
	@Autowired
	private SysDictItemControl sysDictItemControl;
	
	 /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sysDictItemControl).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }
    
    /**
     * 数据字典明细添加
     * @throws Exception
     */
    @Test
    public void test_001_operateItem() throws Exception {
    	SysDictItem sysDictItem = new SysDictItem();
    	sysDictItem.setOptionValue("optionValue");
    	sysDictItem.setItemShowname("itemShowname");
    	sysDictItem.setGroupId(47L);
    	sysDictItem.setSortNo(1L);
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/dictItem/operate");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysDictItem))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典明细更新
     * @throws Exception
     */
    @Test
    public void test_002_operateItem() throws Exception {
    	SysDictItem sysDictItem = new SysDictItem();
    	sysDictItem.setOptionValue("optionValue");
    	sysDictItem.setItemShowname("itemShowname");
    	sysDictItem.setGroupId(47L);
    	sysDictItem.setSortNo(1L);
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/dictItem/operate");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysDictItem))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典明细删除
     * @throws Exception
     */
    @Test
    public void test_003_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/dictItem/operate/21,22");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据ID查看字典类型(存在id)
     * @throws Exception
     */
    @Test
    public void test_004_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/operate/23");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据ID查看字典类型(不存在id)
     * @throws Exception
     */
    @Test
    public void test_005_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/operate/666");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    
    
    /**
     * 根据groupCode查看字典类型的item(存在记录)
     * @throws Exception
     */
    @Test
    public void test_006_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/searchByCode/orgLevel");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据groupCode查看字典类型的item(不存在记录)
     * @throws Exception
     */
    @Test
    public void test_007_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/searchByCode/NotExist");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据groupId查看字典类型的item(存在记录)
     * @throws Exception
     */
    @Test
    public void test_008_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/searchById/47");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据groupId查看字典类型的item(不存在记录)
     * @throws Exception
     */
    @Test
    public void test_009_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictItem/searchById/471");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据groupId查看字典类型的item(不存在记录)
     * @throws Exception
     */
    @Test
    public void test_010_operateItem() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/dictItem/list");
    	MvcResult mvcResult = mockMvc.perform(request
    			.param("EQ_groupCode", "orgType")
    			.param("EQ_optionValue", "1")
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
}
