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
public class DictRestControllerTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private MockMvc mockMvc;

    private String token;
    @Autowired
    private DictRestController dictRestController;

    /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(dictRestController).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 根据code和value去获取具体的字典细项
     * @throws Exception
     */
    @Test
    public void test_001_getDictDetailByCodeAndValue() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/dict/getDictDetailByCodeAndValue")
                .param("code", "mingzu")
                .param("value","1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

    /**
     * 根据code和value去获取具体的字典细项
     * @throws Exception
     */
    @Test
    public void test_002_getDictDetailByCode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/dict/getDictDetailByCode")
                .param("code", "mingzu")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.info("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }

}
