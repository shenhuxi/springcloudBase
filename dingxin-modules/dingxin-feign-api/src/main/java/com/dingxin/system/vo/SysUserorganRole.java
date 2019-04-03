package com.dingxin.system.vo;



public class SysUserorganRole  {	

    private long id;
	
	private long sysUserorganId;	
	
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
