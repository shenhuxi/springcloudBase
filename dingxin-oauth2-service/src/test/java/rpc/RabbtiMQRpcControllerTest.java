package rpc;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
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

import com.dingxin.OAuth2ServiceApplication;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.util.TokenUtil;
import com.dingxin.common.vo.log.SysOperateLogVo;
import com.dingxin.system.rpc.MqRestApi;



/**  
* @ClassName: RabbtiMQRpcControllerTest  
* @Description: Rabbitmq对外接口测试  测试结果正常
* @author xh  
* @date 2018年7月10日10:48:29 
*    
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RabbtiMQRpcControllerTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
	public MqRestApi mqRestApi;
	
	  /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
    
    @Test
    public void test_001() {
    	SysOperateLogVo logVo = new SysOperateLogVo();
    	logVo.setIp("127.0.0.1");
    	logVo.setOperateBusiness("operateBusiness");
    	logVo.setOperateContent("operateContent");
    	logVo.setOperateDate(new Date());
    	logVo.setOperateName("operateName");
    	logVo.setOperateResult("operateResult");
    	logVo.setUrl("url");
    	logVo.setUserName("userName");
    	ResultObject<SysOperateLogVo> resultObject = mqRestApi.sendLog(logVo);
    	Assert.assertEquals("请求错误", 200, resultObject.getCode().intValue());
    }
}
