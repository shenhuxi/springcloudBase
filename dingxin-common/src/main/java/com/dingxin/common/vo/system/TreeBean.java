package com.dingxin.common.vo.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
* Title: TreeBean 
* Description: 公共树JavaBean
* @author dicky  
* @date 2018年6月12日 下午7:07:28
 */
public class TreeBean implements Serializable{
	private static final long serialVersionUID = -162868003988764535L;
	private String key;
	private String title;
	private String parentId;
	private String parentName;
	private String isParent;
	private List<TreeBean> children = new ArrayList<>();
	
	public TreeBean(String key, String title, String parentId, String isParent) {
		this.key = key;
		this.title = title;
		this.parentId = parentId;
		this.isParent = isParent;
	}
	
	public TreeBean(String key, String title, String parentId, String isParent,String parentName) {
		this.key = key;
		this.title = title;
		this.parentId = parentId;
		this.isParent = isParent;
		this.parentName=parentName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public List<TreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}

	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		return "TreeBean [key=" + key + ", title=" + title + ", parentId=" + parentId + ", parentName=" + parentName
				+ ", isParent=" + isParent + ", children=" + children + "]";
	}

	

}
