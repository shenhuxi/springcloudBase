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
import com.dingxin.common.vo.system.SysUserOrganVo;
import com.dingxin.system.rpc.IdentRestApi;
/**  
* @ClassName: DictRpcControllerTest  
* @Description: 身份对外接口测试  测试结果：134因数据库无数据未测试完成，但能正常运行，256测试正常，后期优化 
* @author luozb  
* @date 2018年7月9日 下午6:23:53  
*    
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IdentRpcControllerTest {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
    private IdentRestApi identRestApi;

    /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
 
   @Test
    public void test_003_identRestApi() throws Exception {    	   	
	   SysUserOrganVo sysUserOrganVo1 = identRestApi.getCurrentIdentInfo();
       log.info("sysUserOrganVo1:"+ sysUserOrganVo1);
//--------------------------------------------------------------------------
       List<SysUserOrganVo>  sysUserOrganVos2=identRestApi.getCurrentIdentList();
       log.info("sysUserOrganVos2:"+ sysUserOrganVos2);
     //--------------------------------------------------------------------------
       SysUserOrganVo  sysUserOrganVo3=identRestApi.getDefaultIdentByUsername("admin");
       log.info("sysUserOrganVo3:"+ sysUserOrganVo3);
     //--------------------------------------------------------------------------
       SysUserOrganVo  sysUserOrganVo4=identRestApi.getDefaultIdentByUserId(1L);
       log.info("sysUserOrganVo4:"+ sysUserOrganVo4);
     //--------------------------------------------------------------------------
       List<SysUserOrganVo>  sysUserOrganVos5=identRestApi.geIdentListByUsername("lisi");
       log.info("sysUserOrganVos5:"+ sysUserOrganVos5);
     //--------------------------------------------------------------------------
       List<SysUserOrganVo>  sysUserOrganVos6=identRestApi.getIdentListByUserId(1L);
       log.info("sysUserOrganVos6:"+ sysUserOrganVos6);
       
    }
}



