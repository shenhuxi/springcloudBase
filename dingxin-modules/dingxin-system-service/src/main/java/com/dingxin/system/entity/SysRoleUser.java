package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * SysRoleUser
 * @author shixh
 */
@Entity
@ApiModel
public class SysRoleUser {
	
	@Id
	@GeneratedValue
    private long id;
	@ApiModelProperty(value="用户编号")
	private long sysUserId;
	@ApiModelProperty(value="角色编号")
	private long sysRoleId;
	
	public SysRoleUser() {}
	
	public SysRoleUser(long sysUserId,long sysRoleId) {
		
		this.sysUserId=sysUserId;
		this.sysRoleId=sysRoleId;
	}

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

	public long getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	
}
