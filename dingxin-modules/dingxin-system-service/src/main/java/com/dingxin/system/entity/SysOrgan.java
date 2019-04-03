package com.dingxin.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dingxin.data.jpa.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 组织机构(网公司，省级，市级3级)
 * @author shixh
 *
 */
@Entity
@ApiModel
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SysOrgan extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 6692236902072143598L;
	
	@NotNull
	@ApiModelProperty(value="机构名称",required=true)
	private String orgName; 
	
	@NotNull
	@ApiModelProperty(value="机构代码",required=true)
	private String orgCode;
	
	@ApiModelProperty(value="机构类型（职能部门,非职能部门等，来源于数据字典）")
	private int orgType;  
	
	@NotNull
	@ApiModelProperty(value="上级机构Id",required=true)
	private long parentId;
	
	@NotNull
	@ApiModelProperty(value="机构级别(网公司，省级，市级)",required=true)
	private int orgLevel;	
	
	@NotNull
	@ApiModelProperty(value="企业级别(A级，B级)",required=true)
	private String entLevel;
	
	@ApiModelProperty("机构地址")
	private String orgAddress; 
	
	@ApiModelProperty("机构描述")
	private String orgDesc;    
	
	@ApiModelProperty("机构电话")
	private String orgPhone;   
	
	@Column(columnDefinition="int default 1",nullable=false)
	@ApiModelProperty("是否统一管理部门(0-是，1-否,默认为1)")
	private int isUnified;	
	
	@ApiModelProperty("排序号")
	private Long sortNo;  
	
	@ApiModelProperty("是否是父级(0-是，1-否)")
	private int isParent;
	
	@Transient
	@ApiModelProperty("子孙级机构对象集合")
	private List<SysOrgan> childrens;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public int getOrgType() {
		return orgType;
	}

	public void setOrgType(int orgType) {
		this.orgType = orgType;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(int orgLevel) {
		this.orgLevel = orgLevel;
	}

	public String getEntLevel() {
		return entLevel;
	}

	public void setEntLevel(String entLevel) {
		this.entLevel = entLevel;
	}

	public String getOrgAddress() {
		return orgAddress;
	}

	public void setOrgAddress(String orgAddress) {
		this.orgAddress = orgAddress;
	}

	public String getOrgDesc() {
		return orgDesc;
	}

	public void setOrgDesc(String orgDesc) {
		this.orgDesc = orgDesc;
	}

	public String getOrgPhone() {
		return orgPhone;
	}

	public void setOrgPhone(String orgPhone) {
		this.orgPhone = orgPhone;
	}

	public int getIsUnified() {
		return isUnified;
	}

	public void setIsUnified(int isUnified) {
		this.isUnified = isUnified;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public List<SysOrgan> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<SysOrgan> childrens) {
		this.childrens = childrens;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}
	
	
 
}
