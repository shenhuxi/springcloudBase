package com.dingxin.system.vo;

import java.util.List;


public class SysPermission{
	
	public static final String button = "button";
	public static final String menu = "menu";
	private Long id;
 
    private String code;
	
    private String name;
    private String description;	
	
    private String url;	
	
    private String method;
    
	
    private String permissionType ; 
    
	private Integer sortNo; 
    
    private Long parentId;
    
    private String parentName;     
    
    private Integer isParent;
    
    private SysPermission parentPermission; 
    
	private List<SysPermission> childrens;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<SysPermission> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SysPermission> childrens) {
		this.childrens = childrens;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public SysPermission getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(SysPermission parentPermission) {
		this.parentPermission = parentPermission;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
	
  
}
