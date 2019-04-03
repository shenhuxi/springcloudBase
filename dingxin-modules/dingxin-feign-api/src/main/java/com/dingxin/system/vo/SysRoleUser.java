package com.dingxin.system.vo;

/**
 * SysRoleUser
 * @author lzb
 */
public class SysRoleUser  {
	
	private long id;
	private long sysUserId;
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
