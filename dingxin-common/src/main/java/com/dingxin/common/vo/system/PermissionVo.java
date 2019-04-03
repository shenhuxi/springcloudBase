package com.dingxin.common.vo.system;

import java.util.List;

/**
 * 描述:
 * 作者: qinzhw
 * 创建时间: 2018/6/20 10:46
 */
public class PermissionVo {

	public static final String button = "button";
	public static final String menu = "menu";

	private String id;
	private String code;
	private String name;
	private String description;
	private String url;
	private String method;
	private String permissionType;
	private Integer sortNo;
	private Long parentId;
	private String parentName;
	private String appName;
	private Integer isParent;
	private PermissionVo parentPermission;
	private List<PermissionVo> childrens;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public List<PermissionVo> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<PermissionVo> childrens) {
		this.childrens = childrens;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	public PermissionVo getParentPermission() {
		return parentPermission;
	}

	public void setParentPermission(PermissionVo parentPermission) {
		this.parentPermission = parentPermission;
	}

}
