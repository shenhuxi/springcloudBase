package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.controller.BaseController;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.entity.SysOrgan;
import com.dingxin.system.service.organ.SysOrganService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 描述: 对外提供机构api
 * 作者: qinzhw
 * 创建时间: 2018/6/26 10:51
 */
@RestController
@RequestMapping("/org")
@Api(tags = "对外机构查询API")
public class OrgRestController extends BaseController {

    @Autowired
    private SysOrganService organService;


    /**
     * 获取当前机构
     * @return SysOrgan
     * @throws Exception
     */
    @RequestMapping(value = "/getCurrentOrg", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前机构", notes = "")
    public SysOrgan getCurrentOrg() throws Exception {
        long orgId = getLoginUser().getOrgId();
        return organService.findOne(orgId);
    }

    /**
     * 获取动态树(顶级节点和一级节点)
     * @return
     */
    @RequestMapping(value = "/getDynamicTree", method = RequestMethod.GET)
    @ApiOperation(value = "获取动态树", notes = "")
    public List<TreeBean> getDynamicTree(){
        return organService.queryDynamicTree();
    }

    /**
     * 根据id获取子树
     * @param id
     * @return
     */
    @RequestMapping(value = "/getDynamicTreeById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取子树", notes = "")
    public List<TreeBean> getDynamicTreeById(@PathVariable("id") Long id){
        List<TreeBean> ots= organService.queryOrgTreeByParentId(id);
        //todo
        return ots;
    }


    /**
     * 根据机构id获取机构
     * @return id
     * @throws Exception
     */
    @RequestMapping(value = "/getOrgById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据机构id获取机构", notes = "")
    public SysOrgan getOrgById(@PathVariable("id") Long id) throws Exception {
        return organService.findOne(id);
    }

    /**
     * 获取机构列表
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrgList", method = RequestMethod.GET)
    @ApiOperation(value = "获取机构列表", notes = "")
    public List<SysOrgan> getOrgList() throws Exception {
        return organService.findAll();
    }

    /**
     * 根据机构id 获取机构子列表不含孙节点
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getOrgListByParentId/{parentId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据机构id 获取机构子列表不含孙节点", notes = "")
    public List<SysOrgan> getOrgListByParentId(@PathVariable("parentId") Long parentId) throws Exception {
        return organService.findByParentId(parentId);
    }

    /**
     * 获取静态树
     * @return
     */
    @RequestMapping(value = "/getStaticTree", method = RequestMethod.GET)
    @ApiOperation(value = "获取静态树", notes = "")
    public List<TreeBean> getStaticTree(){
        List<TreeBean> tbs =organService.searchAllOrgTree();
        return tbs;
    }

    /**
     * 根据id获取静态树(完整子树)
     * @param id
     * @return
     */
    @RequestMapping(value = "/getStaticTreeById/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据id获取静态树(完整子树)", notes = "")
    public List<TreeBean> getStaticTreeById(@PathVariable("id") Long id){
        return organService.queryOrgStaticTreeByParentId(id);
    }
}