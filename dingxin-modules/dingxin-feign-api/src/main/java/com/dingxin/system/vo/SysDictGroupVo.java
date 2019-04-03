package com.dingxin.system.vo;

import java.util.List;


/**  
* @ClassName: SysDictGroup  
* @Description: 数据字典类型信息表
* @author luozb  
* @date 2018年6月13日 下午7:46:29  
*    
*/


public class SysDictGroupVo{
	private Long id;
	private Long orgId;
	private Integer deleteState = 0;
	private String dictName;	
	private String dictCode;
	private Long sortNo;
	private Long parentId;
	private String parentName;
	private int isParent;
	private String remark;
	private List<SysDictGroupVo> children;	

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

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	
	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public int getIsParent() {
		return isParent;
	}

	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<SysDictGroupVo> getChildren() {
		return children;
	}

	public void setChildren(List<SysDictGroupVo> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "SysDictGroup [id=" + id + ", orgId=" + orgId + ", deleteState=" + deleteState + ", dictName=" + dictName
				+ ", dictCode=" + dictCode + ", sortNo=" + sortNo + ", parentId=" + parentId + ", parentName="
				+ parentName + ", isParent=" + isParent + ", remark=" + remark + ", children=" + children + "]";
	}

	
}
