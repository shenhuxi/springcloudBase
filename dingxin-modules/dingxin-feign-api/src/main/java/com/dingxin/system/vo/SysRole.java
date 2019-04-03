package com.dingxin.system.vo;

/**
 * 角色
 * @author lzb
 *
 */
public class SysRole {
	
	private Long id;

	private String name; 
	
    private String code;
	
    private String description;
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	

}
