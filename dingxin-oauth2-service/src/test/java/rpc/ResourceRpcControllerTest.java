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
import com.dingxin.common.util.TokenUtil;
import com.dingxin.system.rpc.ResourceRestApi;
import com.dingxin.system.vo.SysPermission;


/**  
* @ClassName: ResourceRpcControllerTest  
* @Description: 测试结果：正常通过 
* @author luozb  
* @date 2018年7月9日 下午6:27:22  
*    
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ResourceRpcControllerTest {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
    private ResourceRestApi resourceRestApi;

    /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
 
   @Test
    public void test_005_resourceRestApi() throws Exception {  
	   log.info("token:   "+token);
	   
       List<SysPermission> sysPermissions1 = resourceRestApi.getPermissionsByUserId(1L);
       log.info("sysPermissions1@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+ sysPermissions1);
//--------------------------------------------------------------------------
       List<SysPermission> sysPermissions2 = resourceRestApi.getPermissionsByIdentId(2L);       
       log.info("sysPermissions2&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:"+ sysPermissions2);
    }
}



