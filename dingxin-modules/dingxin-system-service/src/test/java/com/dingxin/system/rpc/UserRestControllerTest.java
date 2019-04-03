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
public class UserRestControllerTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private MockMvc mockMvc;

    private String token;
    @Autowired
    private UserRestController userRestController;

    /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 获取当前用户
     * @throws Exception
     */
    @Test
    public void test_001_getCurrentUser() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getCurrentUser")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 根据账号查询用户
     * @throws Exception
     */
    @Test
    public void test_002_getUserByAccount() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserByAccount/admin")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);

    }

    /**
     * 根据userId查询用户
     * @throws Exception
     */
    @Test
    public void test_003_getUserById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserById/5")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取系统用户列表
     * @throws Exception
     */
    @Test
    public void test_004_getUserList() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserList")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取当前机构的用户列表
     * @throws Exception
     */
    @Test
    public void test_005_getUserListByCurrentOrg() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserListByCurrentOrg")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }


    /**
     * 根据 用户Id列表 查询用户列表
     * @throws Exception
     */
    @Test
    public void test_006_getUserListByIds() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/getUserListByIds")
                .param("userId","1")
                .param("userId","2")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);

    }

    /**
     * 根据 机构Id 查询用户列表
     * @throws Exception
     */
    @Test
    public void test_007_getUserListByOrgId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserListByOrgId/0")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取当前所属机构下的所有用户（包括引用身份）
     * @throws Exception
     */
    @Test
    public void test_008_getUserListWithRefByCurrentOrg() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserListWithRefByCurrentOrg")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 获取机构ID的所有用户（包括引用身份）
     * @throws Exception
     */
    @Test
    public void test_009_getUserListWithRefByOrgId() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/getUserListWithRefByOrgId/0")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

}
