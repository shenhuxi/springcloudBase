package com.dingxin.system.service.organ;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.BaseTreeUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysOrgan;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.repository.organ.SysOrganRepository;
import com.dingxin.system.repository.user.SysUserRepository;

/**
 * 组织机构service
 * 
 * @author luozb
 */
@Service
@Transactional(rollbackFor = RestException.class)
public class SysOrganService extends CommonService<SysOrgan, Long> {

	@Autowired
	private SysOrganRepository sysOrgRepository;

	@Autowired
	private SysUserRepository sysUserRepository;

	@Override
	public CommonRepository<SysOrgan, Long> getCommonRepository() {
		return sysOrgRepository;
	}

	/**
	 * 保存,判断重复名字,当新增的机构有父级编号时，找到该父级机构，并将其isparent字段设置为0（是）
	 * 
	 * @param sysOrgan
	 * @return
	 */
	public ResultObject checkAndSave(SysOrgan sysOrgan) {
		List<SysOrgan> sysOrgs = sysOrgRepository.findByOrgNameOrOrgCode(sysOrgan.getOrgName(), sysOrgan.getOrgCode());
		if (!CollectionUtils.isEmpty(sysOrgs)) {
			return ResultObject.fail("请不要添加重复数据！");
		}
		
		SysOrgan sos = sysOrgRepository.findById(sysOrgan.getParentId());
		if (sos != null) {
			sos.setIsParent(0);
			sysOrgRepository.saveAndFlush(sos);
		} else {
			return ResultObject.fail("请先录入父级机构！");
		}
		sysOrgan = sysOrgRepository.save(sysOrgan);
		return ResultObject.ok("操作成功!", sysOrgan);
	}
// 1.判断机构下是否存在用户,如有则不能删除！
	//2.通过父级编号=传入的id找到所有子级节点，如果不为空则说明节点下存在子节点不能删除，如果为空说明该id没有子节点，可以删除
	//3.在可以删除的前提下，查询该节点是否有父级，如果父级机构下的子级节点全部删除了，需将父级机构的isParent字段置为1
	public ResultObject deleteByIds(String orgIds) {
		String[] strArr = orgIds.split(",");
		List<String> list = Arrays.asList(strArr);
		List<Long> longList = new ArrayList<>();
		for (String str : list) {
			long i = Long.parseLong(str);
			longList.add(i);
		}
		for (long id : longList) {
			List<SysUser> sysUsers = sysUserRepository.findByOrgId(id);
			if (!CollectionUtils.isEmpty(sysUsers))
				return ResultObject.fail("机构下存在用户,不能进行删除！");
			
			List<SysOrgan> sysOrgans = sysOrgRepository.findByParentId(id);
			if (!CollectionUtils.isEmpty(sysOrgans))
				return ResultObject.fail("机构下存在子机构,不能进行删除！");
			
			SysOrgan sos = sysOrgRepository.findById(id);
			List<SysOrgan> soes = sysOrgRepository.findByParentIdAndIdNotIn(sos.getParentId(), longList);
			if (!CollectionUtils.isEmpty(soes)) {
				SysOrgan psos = sysOrgRepository.findById(sos.getParentId());
				psos.setIsParent(1);
				sysOrgRepository.saveAndFlush(psos);
			}
		}
		sysOrgRepository.deleteByIdIn(longList);
		return ResultObject.ok("删除成功!");
	}

	public ResultObject checkAndUpdate(SysOrgan sysOrgan) {
		List<SysOrgan> sysOrgs = sysOrgRepository.findByOrgNameOrOrgCodeAndIdNotIn(sysOrgan.getOrgName(),
				sysOrgan.getOrgCode(), sysOrgan.getId());
		if (!CollectionUtils.isEmpty(sysOrgs)) {
			return ResultObject.fail("机构名字或者机构编码数据重复！");
		}
		sysOrgan = sysOrgRepository.save(sysOrgan);
		return ResultObject.ok("修改成功!", sysOrgan);
	}

	public List<TreeBean> queryOrgTreeByParentId(long parentId) {
		List<SysOrgan> orgList = sysOrgRepository.findByParentId(parentId);
		if (!CollectionUtils.isEmpty(orgList)) {
			List<TreeBean> treeList = new ArrayList<>();
			orgList.forEach(org ->{
				treeList.add(new TreeBean(org.getId()+"",org.getOrgName()
					,org.getParentId()+"",org.getIsParent()+""));
			});
			return treeList;	
		}
		return Collections.emptyList();

	}

	public List<TreeBean> queryDynamicTree() {
		List<SysOrgan> orgList = sysOrgRepository.findAll();
		if (!CollectionUtils.isEmpty(orgList)) {
			List<TreeBean> treeList = new ArrayList<>();
			orgList.forEach(org ->{
				treeList.add(new TreeBean(org.getId()+"",org.getOrgName()
						,org.getParentId()+"",org.getIsParent()+""));
			});
			List<TreeBean> trees = BaseTreeUtil.buildListToTree(treeList);
			for (TreeBean tree: trees) {
				List<TreeBean> children = tree.getChildren();
				if (children != null) {
					for (TreeBean child: children) {
						child.setChildren(null);
					}
				}
			}
			return trees;
		}
		return Collections.emptyList();
	}

	public List<TreeBean> queryOrgStaticTreeByParentId(long parentId) {
		List<SysOrgan> orgList = sysOrgRepository.findAll();
		if (!CollectionUtils.isEmpty(orgList)) {
			List<TreeBean> treeList = new ArrayList<>();
			orgList.forEach(org ->{
				treeList.add(new TreeBean(org.getId()+"",org.getOrgName()
						,org.getParentId()+"",org.getIsParent()+""));
			});

			List<TreeBean> trees = BaseTreeUtil.buildListToTree(treeList);
			for (TreeBean tree: trees) {
                TreeBean node = findParentNode(tree, parentId);
                if (node != null) {
                    return node.getChildren();
                }
            }
        }
		return Collections.emptyList();
	}
	public TreeBean findParentNode(TreeBean node, long parentId) {
        if (node.getKey().equals(Long.toString(parentId))) {
            return node;
        } else {
            List<TreeBean> children = node.getChildren();
            TreeBean tree = null;
            for (TreeBean treeBean: children) {
                tree = findParentNode(treeBean,parentId);
                if (tree != null) {
                    return tree;
                }
            }
            return tree;
        }
	}

	public List<SysOrgan> findByParentId(long id) {
		return sysOrgRepository.findByParentId(id);
	}

	public List<SysOrgan> findAll() {
		return sysOrgRepository.findAll();
	}

	/**
	 * 构建组织机构树
	 * @return
	 */
	public List<TreeBean> searchAllOrgTree() {
		List<SysOrgan> orgList = sysOrgRepository.findAll();
		if (!CollectionUtils.isEmpty(orgList)) {
			List<TreeBean> treeList = new ArrayList<>();
			orgList.forEach(org ->{
				treeList.add(new TreeBean(org.getId()+"",org.getOrgName()
					,org.getParentId()+"",org.getIsParent()+""));
			});
			return BaseTreeUtil.buildListToTree(treeList);
		}
		return Collections.emptyList();
	}

	public SysOrgan findById(Long curOrgId) {
		return sysOrgRepository.findById(curOrgId);
	}

	public List<SysOrgan> findOrgsByParentId(List<Long> ids) {	
		return	sysOrgRepository.findByParentIdIn(ids);	
		
	}
	
	/**
	 * 构建指定的组织机构树
	 * @return
	 */
	public List<TreeBean> searchAppointOrgTree(List<SysOrgan> orgList) {		
		if (!CollectionUtils.isEmpty(orgList)) {
			List<TreeBean> treeList = new ArrayList<>();
			orgList.forEach(org ->{
				treeList.add(new TreeBean(org.getId()+"",org.getOrgName()
					,org.getParentId()+"",org.getIsParent()+""));
			});
			return BaseTreeUtil.buildListToTree(treeList);
		}
		return Collections.emptyList();
	}

	public List<Long> findOrgsByIdAndLayer(Long curOrgId, int layer) {
		Integer a=(int) (curOrgId.longValue()+layer);
		int orgLevel=a.intValue();
		return	sysOrgRepository.findOrgsByIdAndLayer(curOrgId,orgLevel);	
	}
	
}
