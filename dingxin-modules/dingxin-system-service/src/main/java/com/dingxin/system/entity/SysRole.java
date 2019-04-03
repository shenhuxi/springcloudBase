package com.dingxin.system.entity;


import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import com.dingxin.data.jpa.entity.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 角色
 * @author shixh
 *
 */
@Entity
@ApiModel
public class SysRole extends BaseEntity{
	
	private static final long serialVersionUID = -5757862378977122652L;

	@NotNull
	@ApiModelProperty(value="角色名",required=true)
    private String name; 
	
	@NotNull
	@ApiModelProperty(value="角色代码",required=true)
    private String code;
	
	@ApiModelProperty("角色描述")
    private String description;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	

}
