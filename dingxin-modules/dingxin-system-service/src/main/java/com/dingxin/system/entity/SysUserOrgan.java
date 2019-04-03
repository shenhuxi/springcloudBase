package com.dingxin.system.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**  
* @ClassName: SysUserOrgan  
* @Description: 用户机构关联表（用户身份表）  一个用户属于多个机构，一个机构有多个用户
* 
* @author luozb  
* @date 2018年6月7日 下午5:50:02  
*    
*/
@Entity
@ApiModel
public class SysUserOrgan {
	
	@Id
	@GeneratedValue
	@ApiModelProperty("用户机构表编号")
    private long id;
	
	@ApiModelProperty("用户编号")
	private long sysUserId;
	
	@ApiModelProperty("机构编号")
	private long sysOrganId;
	
	public SysUserOrgan() {}
	
	public SysUserOrgan(long sysUserId,long sysOrganId) {
		
		this.sysUserId=sysUserId;
		this.sysOrganId=sysOrganId;
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

	public long getSysOrganId() {
		return sysOrganId;
	}

	public void setSysOrganId(long sysOrganId) {
		this.sysOrganId = sysOrganId;
	}


}
