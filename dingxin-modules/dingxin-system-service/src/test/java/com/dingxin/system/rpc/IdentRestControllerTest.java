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
 * 
* Title: IdentRestControllerTest 
* Description:  身份信息单元测试
* @author dicky  
* @date 2018年6月30日 下午7:37:31
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IdentRestControllerTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private MockMvc mockMvc;

    private static String  token;
    @Autowired
    private IdentRestController identRestController;

    /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(identRestController).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 获取当前用户身份信息
     * @throws Exception
     */
    @Test
    public void testGetCurrentIdentInfo_001() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/getCurrentIdentInfo")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取用户的所有身份
     * @throws Exception
     */
    @Test
    public void testGetCurrentIdentList_002() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/getCurrentIdentList")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据用户名获取默认身份信息
     * @throws Exception
     */
    @Test
    public void testGetDefaultIdentByUsername_003() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/getDefaultIdentByUsername")
        		.param("username", "lisi")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据用户ID获取默认身份信息
     * @throws Exception
     */
    @Test
    public void testGetDefaultIdentByUserId_004() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/getDefaultIdentByUserId")
        		.param("userId", "3")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 根据用户名获取所有身份信息
     * @throws Exception
     */
    @Test
    public void testGeIdentListByUsername_005() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/geIdentListByUsername")
        		.param("username", "lisi")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 根据用户ID获取所有身份信息
     * @throws Exception
     */
    @Test
    public void testGetIdentListByUserId_006() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/ident/getIdentListByUserId")
        		.param("userId", "3")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
}
