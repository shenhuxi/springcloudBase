package com.dingxin.system.vo;

import java.util.List;


public class SysOrgan{
	private Long id;

	private String orgName; 
	
	private String orgCode;

	private int orgType;  

	private long parentId;
	
	private String orgLevel;	
	

	private String entLevel;

	private String orgAddress; 
	
	private String orgDesc;    
	

	private String orgPhone;   
	

	private int isUnified;	
	
	
	private Long sortNo;  
	
	
	private int isParent;
	
	
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

	public String getOrgLevel() {
		return orgLevel;
	}

	public void setOrgLevel(String orgLevel) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
 
}
