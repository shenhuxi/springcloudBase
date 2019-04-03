package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dingxin.system.config.FeignConfig;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.feign.fallback.OrgRestApiFallBack;
import com.dingxin.system.vo.SysOrgan;

/**  
* @ClassName: OrgRestApi  
* @Description: 机构对外接口
* @author luozb  
* @date 2018年7月6日 下午6:18:49  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
fallback =OrgRestApiFallBack.class,
configuration = FeignConfig.class)
public interface OrgRestApi{

  
    /**
     * 获取当前机构
     * @return SysOrgan
     * @throws Exception
     */
   @RequestMapping(value = "/org/getCurrentOrg", method = RequestMethod.GET)
    public SysOrgan getCurrentOrg() throws Exception;

    /**
     * 获取动态树(顶级节点和一级节点)
     * @return
     */
    @RequestMapping(value = "/org/getDynamicTree", method = RequestMethod.GET)
    public List<TreeBean> getDynamicTree();

    /**
     * 根据id获取子树
     * @param id
     * @return
     */
   @RequestMapping(value = "/org/getDynamicTreeById/{id}", method = RequestMethod.GET)
    public List<TreeBean> getDynamicTreeById(@PathVariable("id") Long id);


    /**
     * 根据机构id获取机构
     * @return id
     * @throws Exception
     */
   @RequestMapping(value = "/org/getOrgById/{id}", method = RequestMethod.GET)
    public SysOrgan getOrgById(@PathVariable("id") Long id) throws Exception;
    /**
     * 获取机构列表
     * @return
     * @throws Exception
     */
   @RequestMapping(value = "/org/getOrgList", method = RequestMethod.GET)
    public List<SysOrgan> getOrgList() throws Exception;

    /**
     * 根据机构id 获取机构子列表不含孙节点
     * @return
     * @throws Exception
     */
   @RequestMapping(value = "/org/getOrgListByParentId/{parentId}", method = RequestMethod.GET)
    public List<SysOrgan> getOrgListByParentId(@PathVariable("parentId") Long parentId) throws Exception;
    /**
     * 获取静态树
     * @return
     */
    @RequestMapping(value = "/org/getStaticTree", method = RequestMethod.GET)
    public List<TreeBean> getStaticTree();

    /**
     * 根据id获取静态树(完整子树)
     * @param id
     * @return
     */
    @RequestMapping(value = "/org/getStaticTreeById/{id}", method = RequestMethod.GET)
    public List<TreeBean> getStaticTreeById(@PathVariable("id") Long id);
}