package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * SysPermissionRole
 * @author shixh
 */
@Entity
@ApiModel
public class SysPermissionRole {
	
	@Id
	@GeneratedValue
	@ApiModelProperty("角色权限编号")
    private long id;
	
	@NotNull
	@ApiModelProperty(value="角色编号",required=true)
	private long roleId;
	@NotNull
	@ApiModelProperty(value="权限编号",required=true)
	private long permissionId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public long getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(long permissionId) {
		this.permissionId = permissionId;
	}
	
}
