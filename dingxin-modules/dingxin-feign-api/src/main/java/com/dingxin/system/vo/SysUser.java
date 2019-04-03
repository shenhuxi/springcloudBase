package com.dingxin.system.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.dingxin.common.annotation.Encryption;

import io.swagger.annotations.ApiModelProperty;



/**
 * 用户
 * @author lzb
 */
public class SysUser  {
	private Long id;

	private Long orgId;

	private Integer deleteState = 0;
	
	private String name;

    private String userName;

   
    private String passWord;

    private String job;
    
    private Integer userState = 0;

	private int lockState;  //0-no,1-yes  redis存储(后期换ＭＱ)

    private int mistakeNums;//密码输入错误次数  redis存储(后期换ＭＱ)

    private String idCard;

	private String mobile;

	private String email;	

	private List<Long> roleIds = new ArrayList<Long>();//角色ID

	private List<SysRole> roles= new ArrayList<SysRole>();
	
	public SysUser() {}
	
	public SysUser(String userName,long id) {
		this.userName = userName;
		this.id=id;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Integer getUserState() {
		return userState;
	}

	public void setUserState(Integer userState) {
		this.userState = userState;
	}

	public int getLockState() {
		return lockState;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getDeleteState() {
		return deleteState;
	}

	public void setDeleteState(Integer deleteState) {
		this.deleteState = deleteState;
	}

	public void setLockState(int lockState) {
		this.lockState = lockState;
	}

	public int getMistakeNums() {
		return mistakeNums;
	}

	public void setMistakeNums(int mistakeNums) {
		this.mistakeNums = mistakeNums;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

	public List<SysRole> getRoles() {
		return roles;
	}

	public void setRoles(List<SysRole> roles) {
		this.roles = roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
