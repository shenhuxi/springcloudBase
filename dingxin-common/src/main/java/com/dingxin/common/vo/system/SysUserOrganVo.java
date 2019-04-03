/**
 * 
 */
package com.dingxin.common.vo.system;

/**
 * Title: SysUserOrganVo Description: 身份VO（包含机构信息）
 * 
 * @author dicky
 * @date 2018年6月14日 下午4:38:09
 */
public class SysUserOrganVo {
	private Long id;//身份ID
	
	private String isDefault;//是否默认身份 0是 1否
	
	private Long orgId;//组织机构ID
	
	private String orgName;//机构名称

	private String orgCode;//机构编码

	private Integer orgType;//机构类型

	private String orgAddress;//机构地址

	private String orgDesc;//机构描述

	private String orgPhone;//机构电话

	public SysUserOrganVo() {}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Integer getOrgType() {
		return orgType;
	}

	public void setOrgType(Integer orgType) {
		this.orgType = orgType;
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

	
}
