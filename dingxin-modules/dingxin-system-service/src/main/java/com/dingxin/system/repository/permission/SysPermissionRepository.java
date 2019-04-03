package com.dingxin.system.repository.permission;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.system.entity.SysPermission;

@Repository
public interface SysPermissionRepository extends CommonRepository<SysPermission,Long> {

	public List<SysPermission> findAll();

	@Query(value = "select p.* from sys_user u left join sys_role_user sru on u.id= sru.sys_user_id left join sys_role r on sru.sys_role_id=r.id left join sys_permission_role spr on spr.role_id=r.id left join sys_permission p on p.id =spr.permission_id where u.id= ?1", nativeQuery = true)
	public List<SysPermission> findByAdminUserId(long userId);

	public SysPermission findByUrlLike(String serviceUrl);	

	List<SysPermission> findByParentId(@Param("parentId") long parentId);

	public List<SysPermission> findByNameAndParentId(String name, Long parentId);

	public List<SysPermission> findByNameAndParentIdAndIdNotIn(String name, Long parentId, Long id);

	public SysPermission findByName(String name);

	public SysPermission findByNameAndPermissionType(String name,String permissionType);

	public List<SysPermission> findByPermissionTypeAndAppName(String permissionType,String appName);

	public SysPermission findByUrlAndMethod(String url,String method);

	public SysPermission findByCode(String code);

	public List<SysPermission> findByParentId(Long id);

    @Query(value = "select a.* from sys_permission a start with a.id =:parentId	connect by prior a.id= a.parent_id order by a.id asc ",nativeQuery = true)
		List<SysPermission> queryTreeByParentId(@Param("parentId")long parentId);

	public SysPermission findById(Long parentId);

	public List<SysPermission> findByParentIdAndIdNotIn(Long parentId, List<Long> longList);

	public void deleteByIdIn(List<Long> longList);

	/**
	 * @param identId 身份ID
	 * @return
	 */
	@Query(value = "select	c.* from sys_userorgan_role a,sys_permission_role b ,sys_permission c where a.sys_role_id = b.role_id and b.permission_id= c.id and a.sys_userorgan_id =:identId ", nativeQuery = true)
	public List<SysPermission> findPermissionsByIdentid(@Param("identId")Long identId);

}
