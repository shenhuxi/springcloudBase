package com.dingxin.system.util;


import java.util.ArrayList;
import java.util.List;
import com.dingxin.system.entity.SysPermission;
 

public class PermissionTreeUtil {
	
	public static final int ROOT_ORG=1;
	
 
	public static List<SysPermission> toTree(List<SysPermission> treeList) {
		List<SysPermission> retList = new ArrayList<>();
		for (SysPermission parent : treeList) {
			if (ROOT_ORG==parent.getParentId()) {
				retList.add(findChildren(parent, treeList));
			}
		}
		return retList;
	}
	
	public static List<SysPermission> toTree(List<SysPermission> treeList,long parentId) {
		List<SysPermission> retList = new ArrayList<>();
		for (SysPermission parent : treeList) {
			if (parentId==parent.getParentId()) {
				retList.add(findChildren(parent, treeList));
			}
		}
		return retList;
	}
	

	private static SysPermission findChildren(SysPermission parent, List<SysPermission> treeList) {
		for (SysPermission child : treeList) {
			if (parent.getId()==child.getParentId()) {
				if (parent.getChildrens() == null) {
					parent.setChildrens(new ArrayList<>());
				}
				parent.getChildrens().add(findChildren(child, treeList));
			}
		}
		return parent;
	}
	
}

