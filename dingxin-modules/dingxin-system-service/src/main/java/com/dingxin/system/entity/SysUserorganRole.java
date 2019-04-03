package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**  
* @ClassName: SysUserorganRole  
* @Description: 用户机构角色关联表（用户身份角色表）  根据用户身份(所属机构或部门)分配角色
* 
* @author luozb  
* @date 2018年6月7日 下午5:50:02  
*    
*/
@Entity
@ApiModel
public class SysUserorganRole {
	
	@Id
	@GeneratedValue
	@ApiModelProperty("用户身份角色表编号")
    private long id;
	
	@ApiModelProperty("用户机构表编号")
	private long sysUserorganId;	
	
	@ApiModelProperty("角色编号")
	private long sysRoleId;
	
	public SysUserorganRole() {}
	
	public SysUserorganRole(long sysUserorganId,long sysRoleId) {
		
		this.sysUserorganId=sysUserorganId;
		this.sysRoleId=sysRoleId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSysUserorganId() {
		return sysUserorganId;
	}

	public void setSysUserorganId(long sysUserorganId) {
		this.sysUserorganId = sysUserorganId;
	}

	public long getSysRoleId() {
		return sysRoleId;
	}

	public void setSysRoleId(long sysRoleId) {
		this.sysRoleId = sysRoleId;
	}

	@Override
	public String toString() {
		return "SysUserorganRole [id=" + id + ", sysUserorganId=" + sysUserorganId + ", sysRoleId=" + sysRoleId + "]";
	}

	

}
