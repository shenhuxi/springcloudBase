package com.dingxin.system.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dingxin.common.constant.CommonConstant;
import com.dingxin.common.constant.RedisKeyConstant;
import com.dingxin.common.exceptions.rest.RestException;
import com.dingxin.common.util.BCryptPasswordUtil;
import com.dingxin.common.util.BeanUtil;
import com.dingxin.common.util.ResultObject;
import com.dingxin.data.jpa.repository.CommonRepository;
import com.dingxin.data.jpa.service.CommonService;
import com.dingxin.system.entity.SysOrgan;
import com.dingxin.system.entity.SysRole;
import com.dingxin.system.entity.SysRoleUser;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.entity.SysUserOrgan;
import com.dingxin.system.entity.SysUserorganRole;
import com.dingxin.system.repository.user.SysUserRepository;
import com.dingxin.system.service.role.SysRoleService;
import com.dingxin.system.service.roleuser.SysRoleUserService;
import com.dingxin.system.service.userorgan.SysUserOrganService;
import com.dingxin.system.service.userorganrole.SysUserorganRoleService;

@Service
@Transactional(rollbackFor = RestException.class)
public class SysUserService extends CommonService<SysUser,Long>{
    
	@Resource
	public RedisTemplate<String, Object> redisTemplate;
	
	@Autowired
	private SysRoleService sysRoleService;
	
    @Autowired
    private SysUserRepository userRepository;
	
	@Autowired
	private SysRoleUserService sysRoleUserService;
	
	@Autowired
	private SysUserOrganService sysUserOrganService;
    
	@Autowired
	private SysUserorganRoleService sysUserorganRoleService;
	@Override
	public CommonRepository<SysUser, Long> getCommonRepository() {
		return userRepository;
	}

	/**
	 * 根据用户名查询用户信息
	 * @param username
	 * @return
	 */
    public SysUser findByUserName(String username) {
        return userRepository.findByUserName(username);
    }


    public List<SysUser> findAll() {
        return userRepository.findAll();
    }

    public Page<SysUser> findByOrgId(long orgId,Pageable pageable){
		return userRepository.findByOrgId(orgId, pageable);
    }

    public Page<SysUser> findByOrgIdWithRef(long orgId,Pageable pageable){
		return userRepository.findByOrgIdWithRef(orgId, pageable);
    }

    public List<SysUser> findByIdIn(List<Long> orgId){
		return userRepository.findByIdIn(orgId);
    }
    
	/**
	 * 用户登录
	 * 密码错误6次锁定十分钟
	 * @param userName
	 * @param passWord
	 * @return
	 * @throws Exception
	 */
    public ResultObject checkUser(String userName, String passWord) throws Exception {
		SysUser user = userRepository.findByUserName(userName);
		if (user == null || user.getDeleteState() == CommonConstant.DELETE) {
			return ResultObject.fail("用户不存在!");
		}
		if (user.getUserState() == CommonConstant.STOP_STATE) {
			return ResultObject.fail("用户还未启用");
		}
		if (user.getUserState() == CommonConstant.DESTROY_STATE) {
			return ResultObject.fail("用户已被注销");
		}
		ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
		String lock = (String) valueOperations.get(userName + RedisKeyConstant.USER_LOCK_KEY);
		if ("true".equals(lock)) {
			logger.info("ttl:{}", valueOperations.get(userName + RedisKeyConstant.USER_LOCK_KEY));
			return ResultObject.fail("错误登陆过多,用户已被锁定,十分钟后再次尝试!");
		}
		if (!BCryptPasswordUtil.matches(passWord, user.getPassWord())) {
			String nums = (String) valueOperations.get(userName + RedisKeyConstant.USER_MISTAKENUMS_KEY);
			if (StringUtils.isEmpty(nums)) {
				valueOperations.set(userName + RedisKeyConstant.USER_MISTAKENUMS_KEY, "1",
						RedisKeyConstant.TIME_MINUTES_LOCK, TimeUnit.SECONDS);
			} else {
				if (Integer.parseInt(nums) >= 5) {
					valueOperations.set(userName + RedisKeyConstant.USER_LOCK_KEY, "true",
							RedisKeyConstant.TIME_MINUTES_LOCK, TimeUnit.SECONDS);
					redisTemplate.delete(userName + RedisKeyConstant.USER_MISTAKENUMS_KEY);
					return ResultObject.fail("密码输入错误,已超过6次,将被锁定!");
				} else {
					valueOperations.set(userName + RedisKeyConstant.USER_MISTAKENUMS_KEY, (Integer.parseInt(nums) + 1) + "");
				}
			}
			return ResultObject.fail("密码不正确!");
		}
		
        return ResultObject.ok(user);
		
	}
    
	@Version
	public SysUser saveUserAndRole(SysUser entity) throws Exception {
    	SysUser sysUserDB = userRepository.findByUserNameAndOrgIdAndDeleteState(entity.getUserName(),entity.getOrgId(),CommonConstant.NORMAL);
		SysUser user = null;
		SysUserOrgan sysUserOrgan = null;
    	if (entity.getId() == null) { //add
			if(sysUserDB!=null) {
				throw new RestException("相同机构下存在同名用户("+entity.getUserName()+"),请重新修改名字!");
			}
			entity.setPassWord(BCryptPasswordUtil.encode(entity.getPassWord()));
			user = this.save(entity);
		} else { //update
			SysUser one = userRepository.findOne(entity.getId());
			if (one == null) {
				throw new RestException("更新操作id不存在!");
			}
			if (com.dingxin.common.util.StringUtils.isBlank(entity.getPassWord())) {
				entity.setPassWord(null);
			}else{
				entity.setPassWord(BCryptPasswordUtil.encode(entity.getPassWord()));
			}
			BeanUtil.copyPropertiesIgnoreNull(entity,one);
			user = this.save(one);
			sysUserOrgan = sysUserOrganService.findByUserIdAndOrgId(user.getId(),user.getOrgId());
		}
    	if(sysUserOrgan == null) {
    		sysUserOrgan = new SysUserOrgan(user.getId(),user.getOrgId());//建立身份
    	}
    	sysUserOrgan = sysUserOrganService.save(sysUserOrgan);
    	if(!entity.getRoleIds().isEmpty()) {
    		//删除立用户与角色关系
			int delete = sysRoleUserService.deleteBySysUserId(user.getId());
			//删除用户身份与角色关系
			sysUserorganRoleService.deleteBySysUserorganId(sysUserOrgan.getId());
			logger.debug("删除角色{}个",delete);
			List<SysRoleUser> sysRoleUsers = new ArrayList<>();
			List<SysUserorganRole> sysUserorganRoles = new ArrayList<>();
			for (Long id : entity.getRoleIds()) {
				SysRole sysRole = sysRoleService.findOne(id);
				if(sysRole!=null) {
				//重新建立用户与角色关系
				sysRoleUsers.add(new SysRoleUser(user.getId(),sysRole.getId()));
				//重新建立身份与角色关系
				sysUserorganRoles.add(new SysUserorganRole(sysUserOrgan.getId(),sysRole.getId()));
				}
			}
			sysRoleUserService.saveList(sysRoleUsers);//保存多个
			sysUserorganRoleService.saveList(sysUserorganRoles);//保存多个
    	}
    	return user;
	}

	public List<SysUser> findByCompanyName(final String orgName) {
        List<SysUser> employeeList = userRepository.findAll(new Specification<SysUser>() {
            @Override
			public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
            	Join<SysUser, SysOrgan> companyJoin = root.join("companyList", JoinType.LEFT);
                return cb.equal(companyJoin.get("name"), orgName);

            }
        });
        return employeeList;
	}

	public List<SysRole> findSysRoleById(Long id) {
		return userRepository.findSysRoleBySysUserId(id);
	}

	public SysUser findUserAndRolesByUserId(Long userId) throws IllegalAccessException {
		SysUser sysUser = this.findOne(userId);
		if (sysUser != null) {
			sysUser.setRoles(findSysRoleById(sysUser.getId()));
		}
		return sysUser;
	}

	/**
	 * 根据手机号码查询用户信息
	 * @param mobile
	 * @return
	 */
	public SysUser findByMobile(String mobile) {
		return userRepository.findByMobile(mobile);
	}



	
}
