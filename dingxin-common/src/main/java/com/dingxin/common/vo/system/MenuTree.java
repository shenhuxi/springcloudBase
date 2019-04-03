package com.dingxin.common.vo.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
* Title: MenuTree  
* Description:  菜单树
* @author dicky  
* @date 2018年6月12日 下午6:42:21
 */
@JsonIgnoreProperties({"key","parentId"})
public class MenuTree implements Serializable{

	private static final long serialVersionUID = -162868003988764549L;
	private String key;
	private String title;
	private String link;
	private String parentId;
	private List<MenuTree> children = new ArrayList<>();
	
	public MenuTree(String key, String title, String link, String parentId) {
		this.key = key;
		this.title = title;
		this.link = link;
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	

}
