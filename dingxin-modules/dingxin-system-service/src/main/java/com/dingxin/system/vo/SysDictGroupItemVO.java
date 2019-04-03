package com.dingxin.system.vo;

import java.io.Serializable;


/**  
* @ClassName: SysDictGroupItemVo
* @Description: 数据字典类型明细信息表
* @author luozb  
* @date 2018年6月13日 下午7:46:29  
*    
*/


public class SysDictGroupItemVO implements Serializable{
	private static final long serialVersionUID = 6692236902072156798L;
	
	private String dictCode;	
	private String dictName;
	private String optionValue;
	private String itemShowname;	
	private Long sortNo;//明细表排序
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
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

	
}
