package com.dingxin.system.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.dingxin.data.jpa.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName: SysDictItem
 * @Description: 字典选项明细信息表
 * @author luozb
 * @date 2018年6月13日 下午7:46:29
 * 
 */

@Entity
@ApiModel
public class SysDictItem extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 6692236902892156798L;

	@ApiModelProperty(value = "关联字典类型编码", required = true)
	private String groupCode;

	@NotNull
	@ApiModelProperty(value = "字典选项值", required = true)
	private String optionValue;

	@ApiModelProperty(value = "删除状态（0-正常,1-删除）", name = "deleteState", dataType = "Integer")
	@Column(columnDefinition = "int default 0", nullable = false)
	protected Integer deleteState = 0;

	@NotNull
	@Column(unique = true)
	@ApiModelProperty(value = "字典显示内容", required = true)
	private String itemShowname;

	@ApiModelProperty(value = "排序号", required = true)
	private Long sortNo;

	@ApiModelProperty(value = "备注", required = false)
	private String remark;
	
	@ApiModelProperty(value = "类型id", required = false)
	private Long groupId;

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
