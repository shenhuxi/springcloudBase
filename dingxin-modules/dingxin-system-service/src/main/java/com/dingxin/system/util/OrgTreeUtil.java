package com.dingxin.system.util;

import java.util.ArrayList;
import java.util.List;

import com.dingxin.system.entity.SysOrgan;

public class OrgTreeUtil {
	
	public static final int ROOT_ORG=1;
	
 
	public OrgTreeUtil() {
		super();		
	}

	public static List<SysOrgan> toTree(List<SysOrgan> treeList) {
		List<SysOrgan> retList = new ArrayList<>();
		for (SysOrgan parent : treeList) {
			if (ROOT_ORG==parent.getParentId()) {
				retList.add(findChildren(parent, treeList));
			}
		}
		return retList;
	}
	
	public static List<SysOrgan> toTree(List<SysOrgan> treeList,long parentId) {
		List<SysOrgan> retList = new ArrayList<>();
		for (SysOrgan parent : treeList) {
			if (parentId==parent.getParentId()) {
				retList.add(findChildren(parent, treeList));
			}
		}
		return retList;
	}
	

	private static SysOrgan findChildren(SysOrgan parent, List<SysOrgan> treeList) {
		for (SysOrgan child : treeList) {
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
	


