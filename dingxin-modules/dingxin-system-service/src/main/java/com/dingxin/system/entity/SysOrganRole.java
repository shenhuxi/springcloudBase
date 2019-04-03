package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**  
* @ClassName: 机构角色关联表 
* @Description: 机构角色表，超级管理员可以为机构分配角色 
* @author luozb  
* @date 2018年7月10日 下午2:56:29  
*    
*/
@Entity
@ApiModel
public class SysOrganRole {
	
	@Id
	@GeneratedValue
    private long id;
	@ApiModelProperty(value="机构编号")
	private long sysOrganId;
	@ApiModelProperty(value="角色编号")
	private long sysRoleId;
	
	public SysOrganRole() {}
	
	public SysOrganRole(long sysOrganId,long sysRoleId) {
		
		this.sysOrganId=sysOrganId;
		this.sysRoleId=sysRoleId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public long getSysOrganId() {
		return sysOrganId;
	}

	public void setSysOrganId(long sysOrganId) {
		this.sysOrganId = sysOrganId;
	}

	public long getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}
	
}
