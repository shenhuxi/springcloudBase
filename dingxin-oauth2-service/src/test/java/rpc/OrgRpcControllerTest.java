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
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.rpc.OrgRestApi;
import com.dingxin.system.vo.SysOrgan;
/**  
* @ClassName: DictRpcControllerTest  
* @Description: 机构对外接口测试  测试结果：2378因数据库无数据未测试完成，但能正常运行，1456测试正常，后期优化 
* @author luozb  
* @date 2018年7月9日 下午6:23:53  
*    
*/

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OAuth2ServiceApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrgRpcControllerTest {
	private final Logger log = LoggerFactory.getLogger(getClass());
    private static String token;    
	
	@Autowired
	public HttpServletRequest request;
	
	@Autowired
    private OrgRestApi orgRestApi;

    /**
     * 初始化token
     */
    @Before
    public void before() {
        token = "bearer " + TokenUtil.getAccessToken();
        request.setAttribute("Authorization",token);
      
    }
 
   @Test
    public void test_004_OrgRestApi() throws Exception {    	   	
       SysOrgan sysOrgan1 = orgRestApi.getCurrentOrg();
       log.info("sysOrgan1:"+ sysOrgan1);
      //--------------------------------------------------------------------------
       List<TreeBean> tbs2 = orgRestApi. getDynamicTree();       
       log.info("tbs2:"+ tbs2);
     //--------------------------------------------------------------------------
       List<TreeBean> tbs3 = orgRestApi. getDynamicTreeById(1l);       
       log.info("tbs3:"+ tbs3);
     //--------------------------------------------------------------------------
       SysOrgan sysOrgan4 = orgRestApi.getOrgById(1l);  
       log.info("sysOrgan4:"+ sysOrgan4);
    //--------------------------------------------------------------------------  
       List<SysOrgan> SysOrgans5 = orgRestApi. getOrgList();
       log.info("SysOrgans5:"+ SysOrgans5);
   //-------------------------------------------------------------------------- 
       List<SysOrgan> SysOrgans6 = orgRestApi. getOrgListByParentId(3L);
       log.info("SysOrgans6:"+ SysOrgans6);
   //-------------------------------------------------------------------------- 
       List<TreeBean> tb7= orgRestApi.getStaticTree();
       log.info("tb7:"+ tb7);
   //-------------------------------------------------------------------------- 
       List<TreeBean> tb8= orgRestApi.getStaticTreeById(2L);
       log.info("tb8:"+ tb8);
    }
}



