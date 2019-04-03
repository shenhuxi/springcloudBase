package com.dingxin.system.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dingxin.data.jpa.entity.BusinessEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**  
* @ClassName: SysDictGroup  
* @Description: 数据字典类型信息表
* @author luozb  
* @date 2018年6月13日 下午7:46:29  
*    
*/


@Entity
@ApiModel
public class SysDictGroup extends BusinessEntity implements Serializable{
	private static final long serialVersionUID = 6692236902072156798L;
	
		
	@NotNull
	@Column(unique=true)
	@ApiModelProperty(value="字典名称(类型)",required=true)	
	private String dictName;		
	
	@NotNull
	@Column(unique=true)
	@ApiModelProperty(value="字典编码(类型)",required=true)	
	private String dictCode;
				
	@ApiModelProperty(value="排序号",required=true)
	private Long sortNo;
	
	@NotNull
	@ApiModelProperty(value="上级机构Id",required=true)
	private Long parentId;
	
	@ApiModelProperty(value="父级名称",required=false)
	private String parentName;
	
	@Column(columnDefinition="int default 1",nullable=false)
	@ApiModelProperty(value="是否父级(0-是，1-否)",required=false)
	private int isParent;
	
	@ApiModelProperty(value="备注",required=false)
	private String remark;
	
	@Transient
	private List<SysDictGroup> children;	

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

	public List<SysDictGroup> getChildren() {
		return children;
	}

	public void setChildren(List<SysDictGroup> children) {
		this.children = children;
	}

	
}
