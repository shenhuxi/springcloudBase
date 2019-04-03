package com.dingxin.generate.permission.control;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.util.ResultObject;
import com.dingxin.generate.permission.feign.PermissionFeign;
import com.dingxin.generate.permission.init.PermissionCommandLineRunner;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 权限脚本生成服务
 *
 * @author shixh
 */
@RestController
@RequestMapping(value = "/permissiongen")
@Api(tags = "权限树生成", description = "权限脚本生成")
public class PermissionGenControl {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private Environment env;

//    @Autowired
//    private SysPermissionService sysPermissionService;

    @Autowired
    PermissionCommandLineRunner runner;

    @Autowired
    private PermissionFeign permissionFeign;



//    @GetMapping(value = "/gen")
//    @ApiOperation(value = "脚本生成", notes = "")
//    @UserOperate(name = OperateConstant.SAVE, business = "系统管理-权限脚本-脚本生成")
//    public ResultObject permissionGen() {
//        Map<String, List<SysPermission>> map = AnnotationsScanUtil.scanByUserOperate();
//        Iterator i = map.keySet().iterator();
//        while (i.hasNext()) {
//            String moudle = (String) i.next();
//            SysPermission sysPermissionMenu = sysPermissionService.findByName(moudle);
//
//            if (sysPermissionMenu == null) {
//                SysPermission perm_new = new SysPermission();
//                perm_new.setName(moudle);
//                perm_new.setParentId(0L);
//                perm_new.setIsParent(0);
//                perm_new.setPermissionType("button");
//                perm_new.setSortNo(0);
//                String path = env.getProperty("spring.application.name");
//                perm_new.setCode(path);
//                perm_new.setUrl("menu");
//                sysPermissionMenu = sysPermissionService.save(perm_new);
//            }
//            List<SysPermission> sysPermissions = map.get(moudle);
//            for (SysPermission sysPermission : sysPermissions) {
//                sysPermission.setParentId(sysPermissionMenu.getId());
//            }
//            sysPermissionService.saveAll(sysPermissions);
//        }
//        return ResultObject.ok();
//    }
//
//    @GetMapping(value = "/gen2")
//    @ApiOperation(value = "脚本生成post", notes = "")
//    @UserOperate(name = OperateConstant.SAVE, business = "系统管理-权限脚本-脚本生成2")
//    public ResultObject permissionGen2() {
//        String path = env.getProperty("server.context-path");
//        List<SysPermission> list = AnnotationsScanUtil.scanByControl(path);
//        list.stream().forEach(permission -> {
//            List<SysPermission> childrens = permission.getChildrens();
//            permission.setParentId(0L);
//            if (childrens != null) {
//                SysPermission save = sysPermissionService.saveByCustom(permission);
//                if (save != null) {
//                    saveChild(childrens, save);
//                }
//            }
//        });
//        return ResultObject.ok();
//    }
//
//    public void saveChild(List<SysPermission> list, SysPermission permission) {
//
//        for (SysPermission s : list) {
//            s.setParentId(permission.getId());
//        }
//        for (SysPermission s : list) {
//            SysPermission save = sysPermissionService.saveByCustom(s);
//            if (save == null) {
//                continue;
//            }
//            List<SysPermission> childrens = s.getChildrens();
//            if (childrens != null) {
//                saveChild(childrens,save);
//            }
//        }
//    }


    @GetMapping(value = "/gen")
    @ApiOperation(value = "生成脚本", notes = "请保证id为1的顶级节点存在")
    @UserOperate(name = OperateConstant.SAVE, business = "系统管理-权限管理-生成脚本")
    public ResultObject permissionGen() {
        return runner.gen();
    }

}
