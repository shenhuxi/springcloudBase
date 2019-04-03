package com.dingxin.system.feign.fallback;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.rpc.OrgRestApi;
import com.dingxin.system.vo.SysOrgan;

/**
 * 描述: feign 调用失败处理
 * 作者: lzb
 * 创建时间: 2018/7/04 11:16
 */
@Service
public class OrgRestApiFallBack implements OrgRestApi {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

    /**
     * 获取当前机构
     * @return SysOrgan
     * @throws Exception
     */
	@Override
    public SysOrgan getCurrentOrg() throws Exception {
		logger.error("调用服务异常:getCurrentOrg()");
		return null;
	}

    /**
     * 获取动态树(顶级节点和一级节点)
     * @return
     */
	@Override
    public List<TreeBean> getDynamicTree() {
		logger.error("调用服务异常:getDynamicTree()");
		return null;
	}

    /**
     * 根据id获取子树
     * @param id
     * @return
     */
	@Override
    public List<TreeBean> getDynamicTreeById(@PathVariable("id") Long id) {
		logger.error("调用服务异常:getDynamicTreeById(): {}", id);
		return null;
	}


    /**
     * 根据机构id获取机构
     * @return id
     * @throws Exception
     */
	@Override
    public SysOrgan getOrgById(@PathVariable("id") Long id) throws Exception {
		logger.error("调用服务异常:getOrgById(): {}", id);
		return null;
	}
    /**
     * 获取机构列表
     * @return
     * @throws Exception
     */
	@Override
    public List<SysOrgan> getOrgList() throws Exception {
		logger.error("调用服务异常:getOrgList()");
		return null;
	}

    /**
     * 根据机构id 获取机构子列表不含孙节点
     * @return
     * @throws Exception
     */
	@Override
    public List<SysOrgan> getOrgListByParentId(@PathVariable("parentId") Long parentId) throws Exception {
		logger.error("调用服务异常:getOrgListByParentId(): {}", parentId);
		return null;
	}
    /**
     * 获取静态树
     * @return
     */
	@Override
    public List<TreeBean> getStaticTree() {
		logger.error("调用服务异常:getStaticTree()");
		return null;
	}

    /**
     * 根据id获取静态树(完整子树)
     * @param id
     * @return
     */
	@Override
    public List<TreeBean> getStaticTreeById(@PathVariable("id") Long id) {
		logger.error("调用服务异常:getStaticTreeById(): {}", id);
		return null;
	}
}