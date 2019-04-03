package com.dingxin.system.rpc;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dingxin.system.config.FeignConfig;
import com.dingxin.system.feign.fallback.UserRestApiFallBack;
import com.dingxin.system.vo.SysUser;

/**  
* @ClassName: UserRestApi  
* @Description: 对外提供用户api
* @author luozb  
* @date 2018年7月9日 下午6:21:24  
*    
*/
@FeignClient(name = "dingxin-system-service/system",
fallback = UserRestApiFallBack.class,
configuration = FeignConfig.class)
public interface UserRestApi{

  

    /**
     * 获取当前用户
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/user/getCurrentUser", method = RequestMethod.GET)
    public  SysUser getCurrentUser() throws Exception;

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    @RequestMapping(value = "/user/getUserByAccount/{account}", method = RequestMethod.GET)
    public  SysUser getUserByAccount(@PathVariable("account") String account);

    /**
     * 根据userId查询用户
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getUserById/{userId}", method = RequestMethod.GET)
    public  SysUser getUserById(@PathVariable("userId") Long userId) throws IllegalAccessException;



    /**
     * 获取系统用户列表
     * @return
     */
    @RequestMapping(value = "/user/getUserList", method = RequestMethod.GET)
    public  List<SysUser> getUserList();

    /**
     * 获取当前机构的用户列表
     * @return
     */
    @RequestMapping(value = "/user/getUserListByCurrentOrg", method = RequestMethod.GET)
    public  List<SysUser> getUserListByCurrentOrg() throws Exception;

    /**
     * 根据 用户Id列表 查询用户列表
     * @param userId
     * @return
     */
    @RequestMapping(value = "/user/getUserListByIds", method = RequestMethod.POST)
    public  List<SysUser> getUserListByIds(@RequestParam("userId") List<Long> userId);

    /**
     * 根据 机构Id 查询用户列表
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/user/getUserListByOrgId/{orgId}", method = RequestMethod.GET)
    public  List<SysUser> getUserListByOrgId(@PathVariable("orgId") Long orgId) throws IllegalAccessException;

    /**
     * 获取当前所属机构下的所有用户（包括引用身份）
     * @return
     */
    @RequestMapping(value = "/user/getUserListWithRefByCurrentOrg", method = RequestMethod.GET)
    public  List<SysUser> getUserListWithRefByCurrentOrg() throws Exception;

    /**
     * 获取机构ID的所有用户（包括引用身份）
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/user/getUserListWithRefByOrgId/{orgId}", method = RequestMethod.GET)
    public  List<SysUser> getUserListWithRefByOrgId(@PathVariable("orgId") Long orgId) throws IllegalAccessException;
}