package com.dingxin.common.vo.system;


import java.io.Serializable;

public class SysUserVo implements Serializable{

	private static final long serialVersionUID = 9038316378418415164L;
	
	private long id;
	
	private long orgId;
	
	private Long identId;//身份ID	
	
	private String identName;//身份名称
	
	private String userName;
	
	private String passWord;
	
	private Integer deleteState = 0;	//删除状态（0-正常,1-删除）
	
	private Integer userState = 0; //用户状态:0-停用,1-启用,2-注销
	
	public SysUserVo() {
		
	}
	public SysUserVo(Long id, String userName, String passWord) {
		this.id = id;
		this.userName = userName;
		this.passWord = passWord;
	}
	
	public SysUserVo(String userName, int id) {
		this.id = id;
		this.userName = userName;
	}

	public SysUserVo(long id, long orgId, String userName, String passWord) {
		this.id = id;
		this.orgId = orgId;
		this.userName = userName;
		this.passWord = passWord;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	
	public Integer getDeleteState() {
		return deleteState;
	}

	
	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	public Long getIdentId() {
		return identId;
	}
	public void setIdentId(Long identId) {
		this.identId = identId;
	}
	public String getIdentName() {
		return identName;
	}
	public void setIdentName(String identName) {
		this.identName = identName;
	}

	
}
