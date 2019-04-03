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
 * 描述:
 * 作者: qinzhw
 * 创建时间: 2018-06-26 16:25
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrgRestControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private MockMvc mockMvc;

    private String token;
    @Autowired
    private OrgRestController orgRestController;

    /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(orgRestController).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 获取当前机构
     * @throws Exception
     */
    @Test
    public void test_001_getCurrentOrg() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getCurrentOrg")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取动态树(顶级节点和一级节点)
     * @throws Exception
     */
    @Test
    public void test_002_getDynamicTree() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getDynamicTree")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);

    }

    /**
     * 根据id获取子树
     * @throws Exception
     */
    @Test
    public void test_003_getDynamicTreeById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getDynamicTreeById/1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 根据机构id获取机构
     * @throws Exception
     */
    @Test
    public void test_004_getOrgById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getOrgById/1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取机构列表
     * @throws Exception
     */
    @Test
    public void test_005_getOrgList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getOrgList")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }


    /**
     * 根据机构id 获取机构子列表不含孙节点
     * @throws Exception
     */
    @Test
    public void test_006_getOrgListByParentId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getOrgListByParentId/1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);

    }

    /**
     * 获取静态树
     * @throws Exception
     */
    @Test
    public void test_007_getStaticTree() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getStaticTree")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 根据id获取静态树(完整子树)
     * @throws Exception
     */
    @Test
    public void test_008_getStaticTreeById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/org/getStaticTreeById/1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }


}
