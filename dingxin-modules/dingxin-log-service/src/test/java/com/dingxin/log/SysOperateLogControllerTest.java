package com.dingxin.log;

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
import com.dingxin.controller.SysOperateLogControl;


/**
 *
 * @author xh
 * @date 2018年6月28日 下午3:44:41 
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysOperateLogControllerTest {
	
	private final static Logger logger = LoggerFactory.getLogger(SysOperateLogControllerTest.class);

	private MockMvc mockMvc;

    private String token;
    
    @Autowired
    private SysOperateLogControl sysOperateLogControl;
    
    /**
     * 初始化MockMvc
     */
    @Before
    public void before() throws IOException {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(sysOperateLogControl).build();
        token = "bearer " + TokenUtil.getAccessToken();
    }
    
    /**
     * 日志获取
     * @throws Exception
     */
    @Test
    public void test_001_findById() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/log/1")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.error("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
    
    /**
     * 日志查询
     * @throws Exception
     */
    @Test
    public void test_002_list() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/log/list")
        		.param("EQ_operateName", "search").param("pageNum", "1").param("pageSize", "10")
                .header("Authorization", token)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        logger.error("返回内容:  " + mvcResult.getResponse().getContentAsString());
        Assert.assertEquals("请求错误", 200, status);
    }
}
