package com.dingxin.data.jpa.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * BusinessEntity公共业务对象类
 */
@ApiModel(value = "BusinessEntity", description = "businessEntity对象")
@MappedSuperclass  
public class BusinessEntity extends BaseEntity{
	
	@ApiModelProperty(value = "机构ID", name = "orgId",dataType="Long")
	protected Long orgId;          
	
	@ApiModelProperty(value = "删除状态（0-正常,1-删除）", name = "deleteState",dataType="Integer")
	@Column(columnDefinition="int default 0",nullable=false)
	protected Integer deleteState = 0;	

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}


}
