package com.dingxin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 接口请求日志记录
 * 
 * @author shixh
 *
 */
@Entity
@ApiModel
public class SysOperateLog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty("日志记录表编号")
	protected Long id;

	@ApiModelProperty("操作功能,参照OperateConstant")
	private String operateName;

	@ApiModelProperty("操作具体业务")
	private String operateBusiness;

	@ApiModelProperty("操作内容(用来比较修改之前和修改之后)")
	private String operateContent;

	@ApiModelProperty("访问路径")
	private String url;

	@ApiModelProperty("用户IP")
	private String ip;

	@ApiModelProperty("用户编号")
	private Long userId;

	@ApiModelProperty("用户姓名")
	private String userName;

	@Column(columnDefinition = "varchar(1000)")
	@ApiModelProperty("返回结果")
	private String operateResult;

	@Temporal(TemporalType.TIMESTAMP)
	@org.hibernate.annotations.CreationTimestamp
	@ApiModelProperty("用户操作时间")
	private Date operateDate;

	@Column(columnDefinition = "Integer default 0", nullable = false)
	@ApiModelProperty("请求响应时间 ")
	private long time;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateBusiness() {
		return operateBusiness;
	}

	public void setOperateBusiness(String operateBusiness) {
		this.operateBusiness = operateBusiness;
	}

	public String getOperateContent() {
		return operateContent;
	}

	public void setOperateContent(String operateContent) {
		this.operateContent = operateContent;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

}
