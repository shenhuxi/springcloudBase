package com.dingxin.system.api;

import java.io.IOException;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dingxin.common.util.TokenUtil;
import com.dingxin.system.entity.SysDictItem;
import com.dingxin.system.entity.SysOrgan;
/*import com.dingxin.system.rpc.DictRestApi;
import com.dingxin.system.rpc.OrgRestApi;*/

/**
 * 描述:
 * 作者: qinzhw
 * 创建时间: 2018-06-26 16:25
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DictRestSysApiTest {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    private String token;
    
    /*@Autowired
    private DictRestApi dictRestApi;
    @Autowired
    private OrgRestApi orgRestApi;*/
    /**
     * 初始化token
     */
    @Before
    public void before() throws IOException {     
        token = "bearer " + TokenUtil.getAccessToken();
    }

    /**
     * 根据code和value去获取具体的字典细项
     * @throws Exception
     */
  /*  @Test
    public void test_001_getDictDetailByCodeAndValue() throws Exception {
    	SysDictItem sysDictItem=dictRestApi.getDictDetailByCodeAndValue("orgType", "1");
    	logger.info("sysDictItem:", sysDictItem);
    }
*/
    /**
     *  获取当前机构
     * @throws Exception
     */
   /* @Test
    public void test_002_getCurrentOrg() throws Exception {
    	SysOrgan so=orgRestApi.getCurrentOrg();
    	logger.info("sysOrgan:", so);
    }*/

}
