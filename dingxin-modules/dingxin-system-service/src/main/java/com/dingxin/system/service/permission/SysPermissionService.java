package com.dingxin.system.service.permission;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.BaseTreeUtil;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.util.StringUtils;
import com.dingxin.common.vo.system.MenuTree;
import com.dingxin.common.vo.system.SysPermissionVo;
import com.dingxin.common.vo.system.TreeBean;
import com.dingxin.system.entity.SysPermission;
import com.dingxin.system.repository.permission.SysPermissionRepository;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysPermissionService {

	private static ThreadLocal<HashMap<String,SysPermission>> threadLocalCodeMap
			= new ThreadLocal<HashMap<String,SysPermission>>(){
		@Override
		protected HashMap<String,SysPermission> initialValue() {
			return new HashMap<String,SysPermission>();
		}
	};

	@Autowired
	private SysPermissionRepository sysPermissionRepository;

	public List<SysPermission> findRoles(Long userId) {
		return sysPermissionRepository.findByAdminUserId(userId);
	}

	/**
	 * 根据身份ID获取权限
	 * @param identId
	 * @return
	 */
	public List<SysPermission> findPermissionsByIdentid(Long identId) {
		return sysPermissionRepository.findPermissionsByIdentid(identId);
	}
	public SysPermission save(SysPermission sysPermission) {
		return sysPermissionRepository.save(sysPermission);
	}
	public SysPermission saveByCustom(SysPermission sysPermission) {
		SysPermission db = sysPermissionRepository.findByCode(sysPermission.getCode());

		//清除新增、更新数据。剩余为要删除数据
		HashMap<String, SysPermission> map = threadLocalCodeMap.get();
		if (map.containsKey(sysPermission.getCode())) {
			map.remove(sysPermission.getCode());
			threadLocalCodeMap.set(map);
		}

		if (db == null) {
			return sysPermissionRepository.save(sysPermission);
		}else{
			sysPermission.setAppName(null);//更新时，不更新appname
			BeanUtil.copyPropertiesIgnoreNull(sysPermission,db);
			return sysPermissionRepository.save(db);
		}
	}

	public void gen (List<SysPermission> list,String appName) {
		/**
		 * 查找顶级节点 约定为 id = 1;
		 */
		SysPermission topNode = sysPermissionRepository.findOne(1L);
		if (topNode == null) {
			topNode = new SysPermission();
			topNode.setId(1L);
			topNode.setName("鼎信公共支撑平台");
			topNode.setDescription("鼎信公共支撑平台");
			topNode.setPermissionType("");
			topNode.setCode("ROOT_NODE");
			topNode.setParentId(0L);
			topNode.setIsParent(0);
			topNode.setSortNo(0);
			topNode = sysPermissionRepository.save(topNode);
		}

		List<SysPermission> permissionList = sysPermissionRepository.findByPermissionTypeAndAppName("button",appName);
		HashMap<String,SysPermission> codeMap = new HashMap<>();
		permissionList.forEach(permission -> {
			if (StringUtils.isNotBlank(permission.getCode())) {
				codeMap.put(permission.getCode(), permission);
			}
		});
		threadLocalCodeMap.set(codeMap);

		SysPermission finalTopNode = topNode;
		list.stream().forEach(permission -> {
			List<SysPermission> childrens = permission.getChildrens();
			permission.setParentId(finalTopNode.getId());
			if (childrens != null) {
				SysPermission save = saveByCustom(permission);
				if (save != null) {
					saveChild(childrens, save);
				}
			}
		});
		//删除数据库中permissionType为button的无用数据
		HashMap<String, SysPermission> deleteMap = threadLocalCodeMap.get();
		sysPermissionRepository.deleteInBatch(deleteMap.values());
	}
	public void saveChild(List<SysPermission> list, SysPermission permission) {
		for (SysPermission s : list) {
			s.setParentId(permission.getId());
		}
		for (SysPermission s : list) {
			SysPermission save = saveByCustom(s);
			if (save == null) {
				continue;
			}
			List<SysPermission> childrens = s.getChildrens();
			if (childrens != null) {
				saveChild(childrens,save);
			}
		}
	}

	public SysPermission findByUrlLike(String serviceUrl) {
		return sysPermissionRepository.findByUrlLike(serviceUrl);
	}

	public Page<SysPermission> findAll(Specification<SysPermission> specification, PageRequest pageRequest) {
		return sysPermissionRepository.findAll(specification, pageRequest);
	}

	// 如果是orcal,需将ifnull改成nvl
	public Page getPage(Map<String, Object> paramsMap, PageRequest pageable) {
		StringBuilder sql = new StringBuilder(" select sp.id as id, " + " sp.name as name, "+ " sp.code as code, "
				+ " sp.parent_id as parentId, "+ " ifnull(sp2.name,'无') as parentName, " + " sp.description as description, " + " sp.url as url, "
				+" sp.sort_no as sortNo, "+ " sp.method as method , " + " sp.permission_type as permissionType " + " from sys_permission sp "
				+ " left join sys_permission sp2 " + " on sp.parent_id = sp2.id where 1=1 ");
		return sysPermissionRepository.findPageByNativeSQLAndParams(sql.toString(), paramsMap,SysPermissionVo.class, pageable) ;
	}

	
	/**
	 * 保存,判断重复名字,当新增的权限有父级编号时，找到该父级机构，并将其isparent字段设置为0（是）	 *
	 * @param sysPermission
	 * @return
	 */
	public ResultObject checkAndSave(SysPermission sysPermission) throws RestException {
		List<SysPermission> sysPermissions = sysPermissionRepository.findByNameAndParentId(sysPermission.getName(),
				sysPermission.getParentId());
		if (!CollectionUtils.isEmpty(sysPermissions)) {
			throw new RestException("同一菜单下名字重复！");
		}
		SysPermission permission = sysPermissionRepository.findByUrlAndMethod(sysPermission.getUrl(),
				sysPermission.getMethod());
		if (permission != null) {
			throw new RestException("同一菜单下URL和Method重复！");
		}
		SysPermission permission2 = sysPermissionRepository.findByCode(sysPermission.getCode());
		if (permission2 != null) {
			throw new RestException("同一菜单下Code重复！");
		}

		SysPermission sps = sysPermissionRepository.findById(sysPermission.getParentId());
		if (sps != null) {
			sps.setIsParent(0);
			sysPermissionRepository.saveAndFlush(sps);
		} else {
			return ResultObject.fail("请先录入父级机构！");
		}
		sysPermission=sysPermissionRepository.save(sysPermission);
		return ResultObject.ok("新增成功", sysPermission);
	}

	public SysPermission checkAndUpdate(SysPermission entity) throws RestException {
		List<SysPermission> sysPermissions = sysPermissionRepository.findByNameAndParentIdAndIdNotIn(entity.getName(),
				entity.getParentId(), entity.getId());
		if (!CollectionUtils.isEmpty(sysPermissions)) {
			throw new RestException("同一 菜单下名字重复！");
		}
		SysPermission sysPermission = sysPermissionRepository.findOne(entity.getId());
		BeanUtil.copyPropertiesIgnoreNull(entity, sysPermission);
		sysPermissionRepository.save(sysPermission);
		return sysPermission;
	}
	
	/**
	 * 根据父节点ID或子权限资源数
	 * @param parentId
	 * @return
	 */
	public List<TreeBean> queryPerTreeByParentId(long parentId) {
		List<SysPermission> syspers = sysPermissionRepository.findByParentId(parentId);
		if (syspers != null) {
			List<TreeBean> treeList = new ArrayList<>();
			syspers.forEach(btree ->{
				treeList.add(new TreeBean(btree.getId()+"",btree.getName()
					,btree.getParentId()+"",btree.getIsParent()+""));
			});
			return treeList;	
		}
		return Collections.emptyList();

	}
	/**
	 * 获取权限资源树
	 * @param paramsMap
	 * @return
	 */
	public List<TreeBean> searchAllPerTree(Map<String, Object> paramsMap) {
		List<SysPermission> permissionList = sysPermissionRepository.findListByParams(paramsMap);
		if (!CollectionUtils.isEmpty(permissionList)) {
			List<TreeBean> treeList = new ArrayList<>();
			permissionList.forEach(btree ->
				{treeList.add(new TreeBean(btree.getId()+"",btree.getName()
						,btree.getParentId()+"",btree.getIsParent()+""));
			});
			return BaseTreeUtil.buildListToTree(treeList);
		}
		return Collections.emptyList();
	}
	/**
	 * 获取前端菜单树
	 * @param paramsMap
	 * @return
	 * @throws ParseException 
	 */
	public List<MenuTree> getMenuTree(Map<String, Object> paramsMap) throws Exception {
		List<SysPermission> permissionList = sysPermissionRepository.findListByParams(paramsMap);
		if (!CollectionUtils.isEmpty(permissionList)) {
			List<MenuTree> treeList = new ArrayList<>();
			permissionList.forEach(btree ->
				{ treeList.add(new MenuTree(btree.getId()+"",btree.getName(),
						btree.getUrl(),btree.getParentId()+""));
			});
			return BaseTreeUtil.buildListToMenuTree(treeList);
		}
		return Collections.emptyList();
	}

	public SysPermission findOne(Long id) {
		return sysPermissionRepository.findOne(id);
	}

	public void delete(SysPermission sysPermission) {
		sysPermissionRepository.delete(sysPermission);
	}

	public SysPermission findByName(String name) {
		return sysPermissionRepository.findByName(name);
	}

	public void saveAll(List<SysPermission> sysPermissions) {
		for (SysPermission sysPermission : sysPermissions) {
			SysPermission db = sysPermissionRepository.findByNameAndPermissionType(sysPermission.getName(),sysPermission.getPermissionType());
			if (db == null) {
				sysPermissionRepository.save(sysPermission);
			}
		}
	}

	// 如果父级权限下的子级节点全部删除了，需将父级权限的isParent字段置为1
	public ResultObject deleteByIds(String perIds) throws RestException {
		String[] strArr = perIds.split(",");
		List<String> list = Arrays.asList(strArr);
		List<Long> longList = new ArrayList<>();
		for (String str : list) {
			long i = Long.parseLong(str);
			longList.add(i);
		}
		for (long id : longList) {		
			SysPermission sysPermission = sysPermissionRepository.findById(id);
			if (sysPermission == null) {
				throw new RestException("权限数据不存在，删除失败!");
			}
			List<SysPermission> sysPermissions = sysPermissionRepository.findByParentId(id);
			if (!CollectionUtils.isEmpty(sysPermissions)) {
				throw new RestException("该权限下面还有子权限 ，不能进行删除!");
			}

			List<SysPermission> sps = sysPermissionRepository.findByParentIdAndIdNotIn(sysPermission.getParentId(), longList);
			if (CollectionUtils.isEmpty(sps)) {
				SysPermission psp = sysPermissionRepository.findById(sysPermission.getParentId());
				psp.setIsParent(1);
				sysPermissionRepository.saveAndFlush(psp);
			}
		}
		sysPermissionRepository.deleteByIdIn(longList);
		return ResultObject.ok("删除成功!");
	}

	public List<SysPermission> findAll() {
		return sysPermissionRepository.findAll();
	}

	public List<SysPermission> queryTreeByParentId(long parentId) {
		return sysPermissionRepository.queryTreeByParentId(parentId);
	}

}