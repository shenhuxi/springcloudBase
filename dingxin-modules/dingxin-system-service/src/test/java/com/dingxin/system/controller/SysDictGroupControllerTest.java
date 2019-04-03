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
import com.dingxin.system.entity.SysDictGroup;

/**
 * SysDictGroupController 测试类
 *
 * @author xh
 * @date 2018年7月4日 下午4:27:41
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysDictGroupControllerTest {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private MockMvc mockMvc;

	private String token;
	
	@Autowired
	private SysDictGroupControl sysDictGroupControl;
	
	 /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sysDictGroupControl).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 数据字典类型添加
     * @throws Exception
     */
    @Test
    public void test_001_operateGroup() throws Exception {
    	SysDictGroup sysDictGroup = new SysDictGroup();
    	sysDictGroup.setDictCode("testCode");
    	sysDictGroup.setDictName("testName");
    	sysDictGroup.setParentId(1L);
    	sysDictGroup.setSortNo(1L);
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/dictGroup/operate");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysDictGroup))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型 更新
     * @throws Exception
     */
    @Test
    public void test_002_operateGroup() throws Exception {
    	SysDictGroup sysDictGroup = new SysDictGroup();
    	sysDictGroup.setId(47L);
    	sysDictGroup.setDictCode("testCode2");
    	sysDictGroup.setDictName("testName2");
    	sysDictGroup.setParentId(1L);
    	sysDictGroup.setSortNo(1L);
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put("/dictGroup/operate");
    	MvcResult mvcResult = mockMvc.perform(request
    			.contentType(MediaType.APPLICATION_JSON)
    			.content(JSONObject.toJSONString(sysDictGroup))
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型 通过id查询(存在的id)
     * @throws Exception
     */
    @Test
    public void test_003_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/operate/47");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型 通过id查询(不存在的id)
     * @throws Exception
     */
    @Test
    public void test_004_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/operate/999");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型 删除
     * @throws Exception
     */
    @Test
    public void test_005_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete("/dictGroup/operate/48");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型  字典组条件查询
     * @throws Exception
     */
    @Test
    public void test_006_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/dictGroup/list");
    	MvcResult mvcResult = mockMvc.perform(request
    			.param("EQ_dictCode", "testCode")
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型  根据父节点查询 动态字典树查询(父节点存在)
     * @throws Exception
     */
    @Test
    public void test_007_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/search/1");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型  根据父节点查询 动态字典树查询(父节点不存在)
     * @throws Exception
     */
    @Test
    public void test_008_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/search/999");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 数据字典类型  静态树
     * @throws Exception
     */
    @Test
    public void test_009_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/staticTree");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据字典类型ID获取明细(存在记录)
     * @throws Exception
     */
    @Test
    public void test_010_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/findItemByDictId/47");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据字典类型ID获取明细(不存在记录)
     * @throws Exception
     */
    @Test
    public void test_011_operateGroup() throws Exception {
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/dictGroup/findItemByDictId/1");
    	MvcResult mvcResult = mockMvc.perform(request
    			.header("Authorization", token)).andReturn();
    	 int status = mvcResult.getResponse().getStatus();
         logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
         Assert.assertEquals("请求错误", 200, status);
    }

}
