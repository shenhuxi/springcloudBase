package rpc;

import java.util.ArrayList;
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
import com.dingxin.system.rpc.UserRestApi;
import com.dingxin.system.vo.SysUser;


/**  
* @ClassName: UserRpcControllerTest  
* @Description: 测试结果：正常通过
* @author luozb  
* @date 2018年7月9日 下午6:27:49  
*    
*/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRpcControllerTest {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
    private UserRestApi userRestApi;

    /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
 
   @Test
    public void test_002_userRestApi() throws Exception {    	   	
       SysUser currentUser = userRestApi.getCurrentUser();
       log.info("currentUser.getUserName():"+ currentUser.getUserName());
//--------------------------------------------------------------------------
        SysUser user1 = userRestApi.getUserByAccount("lisi");
        log.info("//--------------------------------------------------------");
        log.info("user1:---"+user1);
//--------------------------------------------------------------------------
        SysUser user2 = userRestApi.getUserById(3L);
        log.info("user2:---"+user2);    
//--------------------------------------------------------------------------
        List<SysUser> user3 = userRestApi.getUserList();
        log.info("user3:---"+user3);
//--------------------------------------------------------------------------
        List<SysUser> user4 = userRestApi.getUserListByCurrentOrg();
        log.info("user4:---"+user4);
//--------------------------------------------------------------------------
        List<Long> list=new ArrayList<>();
        list.add(1L);
        list.add(2L);
        list.add(3L);        
        List<SysUser> user5 = userRestApi.getUserListByIds(list);
        log.info("user5:---"+user5);
//--------------------------------------------------------------------------
        List<SysUser> user6 = userRestApi.getUserListByOrgId(1L);
        log.info("user6:---"+user6);
//--------------------------------------------------------------------------
        List<SysUser> user7 = userRestApi.getUserListWithRefByCurrentOrg();
        log.info("user7:---"+user7);
//--------------------------------------------------------------------------
        List<SysUser> user8 = userRestApi.getUserListWithRefByOrgId(1L);
        log.info("user8:---"+user8);
//--------------------------------------------------------------------------
    }
}



