package com.dingxin.system.vo;

/**
 * @ClassName: SysDictItem
 * @Description: 字典选项明细信息表
 * @author luozb
 * @date 2018年6月13日 下午7:46:29
 * 
 */

public class SysDictItemVo {
	private Long id;

	private Long orgId;

	private Integer deleteState = 0;

	private String groupCode;

	private String optionValue;

	private String itemShowname;

	private Long sortNo;

	private String remark;

	private Long groupId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getOptionValue() {
		return optionValue;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getItemShowname() {
		return itemShowname;
	}

	public void setItemShowname(String itemShowname) {
		this.itemShowname = itemShowname;
	}

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	

}
