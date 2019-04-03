package com.dingxin.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dingxin.common.vo.system.MenuTree;
import com.dingxin.common.vo.system.TreeBean;
/**
 * 方法描述：递归构建公共树
 * @param List<TreeBean> treeList
 * @return   List<TreeBean>
 * @time 2018年6月6日 下午6:00:35
 * @author luozb
 */

public class BaseTreeUtil {
	
 
	public BaseTreeUtil() {
		super();		
	}

	/**
	 * 递归构建公共树
	 * @param treeList
	 * @return
	 */
	public static List<TreeBean> buildListToTree(List<TreeBean> treeList) {
		List<TreeBean> listParentTree = new ArrayList<>();
		List<TreeBean> listNotParentTree = new ArrayList<>();
		// 第一步：遍历btrees保存所有数据的id用于判断是不是根节点
		Map<String, String> mapAllId = new HashMap<>();
		Map<String, TreeBean> treeBeanMap = new HashMap<>();
		for (TreeBean tb : treeList) {
			mapAllId.put(tb.getKey(), tb.getKey());
			treeBeanMap.put(tb.getKey(), tb);
		}
		// 第二步：遍历btrees找出所有的根节点和非根节点
		if (!treeList.isEmpty()) {
			for (TreeBean treeBean : treeList) {
				if (!mapAllId.containsKey(treeBean.getParentId())) {
					listParentTree.add(treeBean);
				} else {
					listNotParentTree.add(treeBean);
				}
			}
		}

		// 第三步： 递归获取所有子节点
		if (!listParentTree.isEmpty()) {
			for (TreeBean treeBean : listParentTree) {
				// 添加所有子级
				treeBean.setChildren((getTreeChildTreeBean(listNotParentTree, treeBean.getKey())));
			}
		}
		return listParentTree;
	}
	
	
	/**
	 * 说明方法描述：递归查询子节点,只能查询到子节点，不能查询到孙节点
	 * 
	 * @param childList  子节点
	 * @param parentUuid 父节点id
	 * @return
	 * @time 2018年6月6日 下午3:29:35
	 * @author luozb
	 */
	public static List<TreeBean> getTreeChildTreeBean(List<TreeBean> childList, String parentId) {
		List<TreeBean> listParentTree = new ArrayList<>();
		List<TreeBean> listNotParentTree = new ArrayList<>();
		// 遍历tmpList，找出所有的根节点和非根节点
		if (childList != null && !childList.isEmpty()) {
			for (TreeBean treeBean : childList) {
				// 对比找出父节点
				if (parentId.equals(treeBean.getParentId())) {
					listParentTree.add(treeBean);
				} else {
					listNotParentTree.add(treeBean);
				}

			}
		}
		// 查询子节点
		if (!listParentTree.isEmpty()) {
			for (TreeBean treeBean : listParentTree) {
				// 递归查询子节点
				treeBean.setChildren((getTreeChildTreeBean(listNotParentTree, treeBean.getKey())));

			}
		}
		return listParentTree;
	}
	
	/**
	 * 递归构建菜单树
	 * @param treeList
	 * @return
	 */
	public static List<MenuTree> buildListToMenuTree(List<MenuTree> treeList) {
		List<MenuTree> listParentTree = new ArrayList<>();
		List<MenuTree> listNotParentTree = new ArrayList<>();
		// 第一步：遍历btrees保存所有数据的id用于判断是不是根节点
		Map<String, String> mapAllId = new HashMap<>();
		Map<String, MenuTree> treeBeanMap = new HashMap<>();
		for (MenuTree tb : treeList) {
			mapAllId.put(tb.getKey(), tb.getKey());
			treeBeanMap.put(tb.getKey(), tb);
		}
		// 第二步：遍历btrees找出所有的根节点和非根节点
		if (!treeList.isEmpty()) {
			for (MenuTree treeBean : treeList) {
				if (!mapAllId.containsKey(treeBean.getParentId())) {
					listParentTree.add(treeBean);
				} else {
					listNotParentTree.add(treeBean);
				}
			}
		}

		// 第三步： 递归获取所有子节点
		if (!listParentTree.isEmpty()) {
			for (MenuTree treeBean : listParentTree) {
				// 添加所有子级
				treeBean.setChildren((getTreeChildMenuTree(listNotParentTree, treeBean.getKey())));
			}
		}
		return listParentTree;
	}
	/**
	 * 说明方法描述：递归查询子节点,只能查询到子节点，不能查询到孙节点
	 * 
	 * @param childList  子节点
	 * @param parentUuid 父节点id
	 * @return
	 * @time 2018年6月6日 下午3:29:35
	 * @author luozb
	 */
	public static List<MenuTree> getTreeChildMenuTree(List<MenuTree> childList, String parentId) {
		List<MenuTree> listParentTree = new ArrayList<>();
		List<MenuTree> listNotParentTree = new ArrayList<>();
		// 遍历tmpList，找出所有的根节点和非根节点
		if (childList != null && !childList.isEmpty()) {
			for (MenuTree treeBean : childList) {
				// 对比找出父节点
				if (parentId.equals(treeBean.getParentId())) {
					listParentTree.add(treeBean);
				} else {
					listNotParentTree.add(treeBean);
				}

			}
		}
		// 查询子节点
		if (!listParentTree.isEmpty()) {
			for (MenuTree treeBean : listParentTree) {
				// 递归查询子节点
				treeBean.setChildren((getTreeChildMenuTree(listNotParentTree, treeBean.getKey())));

			}
		}
		return listParentTree;
	}
	
}
	


