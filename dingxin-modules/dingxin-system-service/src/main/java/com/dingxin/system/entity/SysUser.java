package com.dingxin.system.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.dingxin.common.annotation.Encryption;
import com.dingxin.data.jpa.entity.BusinessEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 用户
 * @author shixh
 */
@JsonIgnoreProperties({"lockState","mistakeNums"})
@Entity
@ApiModel
public class SysUser extends BusinessEntity{
	
	private static final long serialVersionUID = -1703630040908311405L;
	
    @NotNull
	@ApiModelProperty("用户姓名")
	private String name;

    @NotNull
	@Column(unique=true)
    @ApiModelProperty("登陆名")
    private String userName;

   
    @ApiModelProperty("密码")
	@Encryption
    private String passWord;

    @ApiModelProperty("工作")
    private String job;
    
    @NotNull
	@ApiModelProperty("用户状态:0-停用,1-启用,2-注销")
    @Column(columnDefinition="int default 0",nullable=false)
    private Integer userState = 0;

	@ApiModelProperty("锁定状态:0-no,1-yes")
    @Transient
	private int lockState;  //0-no,1-yes  redis存储(后期换ＭＱ)

	@ApiModelProperty("密码输入错误次数")
	@Transient
    private int mistakeNums;//密码输入错误次数  redis存储(后期换ＭＱ)

	@ApiModelProperty("身份证")
    @Encryption
    private String idCard;
	
	@ApiModelProperty("手机")
	private String mobile;

	@NotNull
	@ApiModelProperty("邮箱")
	private String email;

	@NotNull
	@Transient	
	private List<Long> roleIds = new ArrayList<Long>();//角色ID

	@Transient
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
