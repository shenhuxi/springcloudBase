package com.dingxin.system.rpc;

import com.dingxin.system.controller.UserBaseController;
import com.dingxin.system.entity.SysOrganRole;
import com.dingxin.system.entity.SysUser;
import com.dingxin.system.service.user.SysUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述: 对外提供用户api
 * 作者: qinzhw
 * 创建时间: 2018/6/26 10:51
 */
@RestController
@RequestMapping("/user")
@Api(tags = "对外用户查询API")
public class UserRestController extends UserBaseController {

    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取当前用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前用户", notes = "")
    public  SysUser getCurrentUser() throws Exception {
        return getLoginSysUser();
    }
    
   

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @RequestMapping(value = "/getUserByAccount/{account}", method = RequestMethod.GET)
    @ApiOperation(value = "根据账号查询用户", notes = "")
    public  SysUser getUserByAccount(@PathVariable("account") String account){
        return sysUserService.findByUserName(account);
    }

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserById/{userId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据userId查询用户", notes = "")
    public  SysUser getUserById(@PathVariable("userId") Long userId) throws IllegalAccessException {
        return sysUserService.findOne(userId);
    }


//    @RequestMapping(value = "/user/validate", method = RequestMethod.POST)
//    public  UserInfo validate(@RequestBody Map<String,String> body){
//        return permissionService.validate(body.get("username"),body.get("password"));
//    }


    /**
     * 获取系统用户列表
     * @return
     */
    @RequestMapping(value = "/getUserList", method = RequestMethod.GET)
    @ApiOperation(value = "获取系统用户列表", notes = "")
    public  List<SysUser> getUserList() {
        return sysUserService.findAll();
    }

    /**
     * 获取当前机构的用户列表
     * @return
     */
    @RequestMapping(value = "/getUserListByCurrentOrg", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前机构的用户列表", notes = "")
    public  List<SysUser> getUserListByCurrentOrg() throws Exception {
        SysUser user = getLoginSysUser();
        Page<SysUser> orgUesrs = sysUserService.findByOrgId(user.getOrgId(),null);
        return orgUesrs.getContent();
    }

    /**
     * 根据 用户Id列表 查询用户列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/getUserListByIds", method = RequestMethod.POST)
    @ApiOperation(value = "根据用户Id列表 查询用户列表", notes = "")
    public  List<SysUser> getUserListByIds(@RequestParam("userId") List<Long> userId) {
        return sysUserService.findByIdIn(userId);
    }

    /**
     * 根据 机构Id 查询用户列表
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/getUserListByOrgId/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据机构Id查询用户列表", notes = "")
    public  List<SysUser> getUserListByOrgId(@PathVariable("orgId") Long orgId) throws IllegalAccessException {
        return sysUserService.findByOrgId(orgId,null).getContent();
    }

    /**
     * 获取当前所属机构下的所有用户（包括引用身份）
     * @return
     */
    @RequestMapping(value = "/getUserListWithRefByCurrentOrg", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前所属机构下的所有用户（包括引用身份）", notes = "")
    public  List<SysUser> getUserListWithRefByCurrentOrg() throws Exception {
        SysUser user = getLoginSysUser();
        Page<SysUser> orgUesrs = sysUserService.findByOrgIdWithRef(user.getOrgId(),null);
        return orgUesrs.getContent();
    }

    /**
     * 获取机构ID的所有用户（包括引用身份）
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/getUserListWithRefByOrgId/{orgId}", method = RequestMethod.GET)
    @ApiOperation(value = "获取机构ID的所有用户（包括引用身份）", notes = "")
    public  List<SysUser> getUserListWithRefByOrgId(@PathVariable("orgId") Long orgId) throws IllegalAccessException {
        Page<SysUser> orgUesrs = sysUserService.findByOrgIdWithRef(orgId,null);
        return orgUesrs.getContent();
    }
    
   
}