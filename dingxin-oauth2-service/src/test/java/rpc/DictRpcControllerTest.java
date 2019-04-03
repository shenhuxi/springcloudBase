package rpc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.dingxin.system.rpc.DictRestApi;
import com.dingxin.system.vo.SysDictItemVo;


/**  
* @ClassName: DictRpcControllerTest  
* @Description: 数据字典对外接口测试  测试结果正常
* @author luozb  
* @date 2018年7月9日 下午6:23:53  
*    
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DictRpcControllerTest {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
    private DictRestApi dictRestApi;

    /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
 
   @Test
    public void test_001_dictRestApi() throws Exception {    	   	
       ResultObject<SysDictItemVo> sysDictItem = dictRestApi.getDictDetailByCodeAndValue("orgType","1");
       
       log.info("sysDictItem:"+ sysDictItem.getData());
//--------------------------------------------------------------------------
       ResultObject<List<SysDictItemVo>> sysDictItems = dictRestApi.getDictDetailByCode("orgType");
       
       log.info("sysDictItems:"+ sysDictItems.getData());
    }
}



