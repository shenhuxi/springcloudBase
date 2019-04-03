package com.dingxin.system.entity;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dingxin.data.jpa.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


@Entity
@ApiModel(description="权限实体")
public class SysPermission extends BaseEntity{
	
	private static final long serialVersionUID = 8134810461321564631L;
	
	public static final String button = "button";
	public static final String menu = "menu";
	
	@NotNull
	@ApiModelProperty(value="权限编号",required=true)
    private String code;
	
	@NotNull
	@ApiModelProperty(value="权限名称",required=true)
    private String name;
	
	@ApiModelProperty("权限描述")
    private String description;	
	
	@ApiModelProperty("访问路径")
    private String url;

	@ApiModelProperty("请求方式")
    private String method;

	@ApiModelProperty("微服务应该contextPath")
    private String appName;

	
    @Column
    @NotNull
    @ApiModelProperty(value="权限类型(0:菜单,1:按钮)",required=true)
    private String permissionType ; 
    
    @Column(columnDefinition="int default 0",nullable=false)
    @ApiModelProperty("排序号(默认为0)")
	private Integer sortNo; 
    
    @NotNull
    @ApiModelProperty(value="父级编号",required=true)
    private Long parentId;
    
    @Transient
    @ApiModelProperty("父级名称")
    private String parentName;     
    
    @Column(columnDefinition="int default 1",nullable=true)
    @ApiModelProperty("是否父级菜单,0是1否")
    private Integer isParent;
    
    @Transient
    @ApiModelProperty("父级权限对象")
    private SysPermission parentPermission; 
    
	@Transient
	@ApiModelProperty("子孙级权限对象集合")
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

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
}
