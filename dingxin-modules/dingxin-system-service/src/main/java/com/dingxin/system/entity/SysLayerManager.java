package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**  
* @ClassName: SysUserOrgan  
* @Description: 分级管理员表（用户编号，机构编号及直辖管理层数（-1不限制，0当前机构，1当前及下级机构，2当前及下二级机构......））
* 
* @author luozb  
* @date 2018年7月11日 下午5:50:02  
*    
*/
@Entity
@ApiModel
public class SysLayerManager {
	
	@Id
	@GeneratedValue
	@ApiModelProperty("分级管理员编号")
    private long id;
	
	@NotNull
	@ApiModelProperty("用户编号")
	private long sysUserId;
	
	@NotNull
	@ApiModelProperty("机构编号")
	private long sysOrganId;
	
	@NotNull
	@ApiModelProperty("直辖管理层数")
	private Integer layer;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public long getSysOrganId() {
		return sysOrganId;
	}

	public void setSysOrganId(long sysOrganId) {
		this.sysOrganId = sysOrganId;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	
	

}
