package com.dingxin.common.vo.system;

/*
@NamedNativeQueries({
	@NamedNativeQuery(
			name="SysPermissionVoQuery",
			query="select c.id as id,c.name as name,decode(b.name,null,'无',b.name) as parentName,c.permission_type as permissionType,c.url as url,c.description as description " + 
					" from sys_permission c " + 
					" left join sys_permission b " + 
					" on b.id = c.parent_id",
			resultSetMapping="withParentName"),
})
@SqlResultSetMappings({
    @SqlResultSetMapping(
       name="SysPermissionVoQuery",
       entities={},
       columns={
           @ColumnResult(name="id"),
           @ColumnResult(name="name"),
           @ColumnResult(name="parentName"),
           @ColumnResult(name="permissionType"),
           @ColumnResult(name="url"),
           @ColumnResult(name="description")
       }
    ),
})
 * */
public class SysPermissionVo {
	
	private String code;
	private String id;
	private String name;
    private String description;
    private String url;
    private String method;
    private String permissionType ;//1-按钮,0-菜单 
    public String parentName;
    public String parentId;
    public String sortNo;
    


	public SysPermissionVo() {}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getPermissionType() {
		return permissionType;
	}



	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}



	public String getParentName() {
		return parentName;
	}



	public void setParentName(String parentName) {
		this.parentName = parentName;
	}



	public String getParentId() {
		return parentId;
	}



	public void setParentId(String parentId) {
		this.parentId = parentId;
	}



	public String getSortNo() {
		return sortNo;
	}



	public void setSortNo(String sortNo) {
		this.sortNo = sortNo;
	}
    
    
	
}
