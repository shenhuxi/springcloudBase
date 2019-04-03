package com.dingxin.generate.permission.util;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.vo.system.PermissionVo;

/**
 * 注解扫描
 * @author shixh
 *
 */
public class AnnotationsScanUtil {
 
	public static final String namespace_common = "/api";//统一公共路径
	public static final String scan_common = "com.dingxin";//统一扫描路径
	public static final String mark = "<权限>";		//一级节点标示

	/**
     * 根据指定目录扫描使用到UserOperate注解的方法生成权限数据
     * @author shixh
     * @return
     */
    public static Map<String,List<PermissionVo>> scanByUserOperate() {
    	Map<String,List<PermissionVo>> map = new HashMap<String,List<PermissionVo>>();
        Reflections reflections = new Reflections(scan_common);
        Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);
        for (Class classes : classesList) {
        	List<PermissionVo> sysPermissions = new ArrayList<PermissionVo>();
        	RequestMapping annotation = (RequestMapping) classes.getAnnotation(RequestMapping.class);
        	if(annotation==null) {
                continue;
            }
        	String rootPath = arrAToString(annotation.value());
        	if(StringUtils.isEmpty(rootPath)) {
                continue;
            }
        	//rootPath = namespace_common+"/"+server+rootPath;
            //得到该类下面的所有方法
            Method[] methods = classes.getDeclaredMethods();
            int rowNum = 1;
            String module ="";
            for (Method method : methods) {
            	UserOperate userOperate = method.getAnnotation(UserOperate.class);
                if (null != userOperate) {
                	String path = "";
                	String requestMethod = "";
                	if("".equals(module)) {
                		module = getModule(userOperate.business());
                	}
                	PostMapping postMapping = method.getAnnotation(PostMapping.class);
                	if(null!=postMapping) {
                		requestMethod="POST";
                		path = arrAToString(postMapping.value());
                	}
                	PutMapping putMapping = method.getAnnotation(PutMapping.class);
                	if(null!=putMapping) {
                		requestMethod="PUT";
                		path = arrAToString(putMapping.value());
                	}
                	DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
                	if(null!=deleteMapping) {
                		requestMethod="DELETE";
                		path = arrAToString(deleteMapping.value());
                	}
                	GetMapping getMapping = method.getAnnotation(GetMapping.class);
                	if(null!=getMapping) {
                		requestMethod="GET";
                		path = arrAToString(getMapping.value());
                	}
					PermissionVo sysPermission = new PermissionVo();
                	sysPermission.setPermissionType(PermissionVo.button);
                	sysPermission.setDescription(userOperate.business());
                	sysPermission.setName(getName(userOperate.business()));
                	sysPermission.setUrl(endToLike(rootPath+path));
                	sysPermission.setMethod(requestMethod);
                	sysPermission.setSortNo(rowNum);
                	sysPermission.setCode(sysPermission.getUrl().replace("/","-") + "-" + sysPermission.getMethod());
                	sysPermissions.add(sysPermission);
                	System.out.println(endToLike(rootPath+path));
                	rowNum++;
                }
            }
            if(StringUtils.isNotEmpty(module) && !sysPermissions.isEmpty()) {
            	if(map.get(module)!=null) {
            		List<PermissionVo> values = map.get(module);
            		values.addAll(sysPermissions);
            		map.put(module,values);
            	}else {
            		map.put(module, sysPermissions);
            	}
            	
            }
            
        }
        return map;
    }
	/**
	 * 根据指定目录扫描使用到UserOperate注解的方法生成权限数据
	 * @author qinzhw
	 * @return
	 */
	public static List<PermissionVo> scanByControl(String contextPath) {
		String appName = contextPath.replace("/","");
		List<PermissionVo> list = new ArrayList<PermissionVo>();
		Reflections reflections = new Reflections(scan_common);
		Set<Class<?>> classesList = reflections.getTypesAnnotatedWith(RestController.class);
		for (Class classes : classesList) {
			RequestMapping annotation = (RequestMapping) classes.getAnnotation(RequestMapping.class);
			if(annotation==null) {
				continue;
			}
			String controlMapping = arrAToString(annotation.value());
			if(StringUtils.isEmpty(controlMapping)) {
				continue;
			}
			//rootPath = namespace_common+"/"+server+rootPath;
			//得到该类下面的所有方法
			Method[] methods = classes.getDeclaredMethods();
			int rowNum = 1;
			String [] modules;
			Map<String,PermissionVo> tmpMap = new HashMap<String,PermissionVo>();
			for (Method method : methods) {
				UserOperate userOperate = method.getAnnotation(UserOperate.class);
				if (null != userOperate) {
					String path = "";
					String requestMethod = "";
					modules = getModules(userOperate.business());
					PermissionVo curParent = null;
					if(modules == null){
						continue;
					}
					if(modules.length > 1) {
						// 多节点处理，modules.length - 1 只处理父节点 ，不处理最后一节
						for (int i = 0; i < modules.length - 1 ; i++) {

							PermissionVo first = null;
							if (i == 0) { // 一级节点
								first = getParent(list,modules,0,0);
								if (first == null) {
									PermissionVo parentButton = new PermissionVo();
									parentButton.setName(modules[0]+mark);
									parentButton.setAppName(appName);
									parentButton.setIsParent(0);
									parentButton.setPermissionType("button");
									parentButton.setSortNo(rowNum);
									parentButton.setCode(modules[0]);
									list.add(parentButton);
									rowNum ++ ;
								}
								continue;
							}

							PermissionVo parent = getParent(list,modules,0,i - 1);
							PermissionVo tmp = getParent(list,modules,0,i);
							if (tmp == null ) {

								PermissionVo button = new PermissionVo();
								StringBuffer description = new StringBuffer();
								for (int j = 0; j <= i; j++) {
									description.append(modules[j]+"-");
								}
								button.setName(modules[i]);
								button.setAppName(appName);
								button.setIsParent(0);
								button.setPermissionType("button");
								button.setSortNo(rowNum);
								button.setCode(description.toString());
								button.setDescription(description.deleteCharAt(description.length() - 1).toString());

								List<PermissionVo> childrens = parent.getChildrens();
								if (childrens == null) {
									childrens = new ArrayList<PermissionVo>();
									parent.setChildrens(childrens);
								}
								parent.getChildrens().add(button);
							}
							rowNum ++ ;
						}

						curParent = getParent(list,modules,0,modules.length - 2);;
					}

					

					PostMapping postMapping = method.getAnnotation(PostMapping.class);
					if(null!=postMapping) {
						requestMethod="POST";
						path = arrAToString(postMapping.value());
					}
					PutMapping putMapping = method.getAnnotation(PutMapping.class);
					if(null!=putMapping) {
						requestMethod="PUT";
						path = arrAToString(putMapping.value());
					}
					DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
					if(null!=deleteMapping) {
						requestMethod="DELETE";
						path = arrAToString(deleteMapping.value());
					}
					GetMapping getMapping = method.getAnnotation(GetMapping.class);
					if(null!=getMapping) {
						requestMethod="GET";
						path = arrAToString(getMapping.value());
					}
					PermissionVo sysPermission = new PermissionVo();
					sysPermission.setPermissionType(PermissionVo.button);
					sysPermission.setDescription(userOperate.business());
					sysPermission.setName(getLastName(userOperate.business()));
					sysPermission.setAppName(appName);
					sysPermission.setUrl(contextPath + endToLike(controlMapping+path));
					sysPermission.setMethod(requestMethod);
					sysPermission.setSortNo(rowNum);
					sysPermission.setIsParent(1);
					sysPermission.setCode(sysPermission.getUrl().replace("/","_") + "_" + sysPermission.getMethod());
					if (curParent == null) {
						list.add(sysPermission);
					} else {
						List<PermissionVo> childrens = curParent.getChildrens();
						if (childrens == null) {
							childrens = new ArrayList<PermissionVo>();
							curParent.setChildrens(childrens);
						}
						curParent.getChildrens().add(sysPermission);
					}

					System.out.println(contextPath+endToLike(controlMapping+path));
					rowNum++;
				}
			}
		}
		return list;
	}

	/**
	 * 描述: 当前方法遍历
	 * 作者: qinzhw
	 * 创建时间: 2018/6/13 15:14
	 */
	private static PermissionVo getParent(List<PermissionVo> list, String[] modules ,int curIndex, int index) {

		if (curIndex > index) {
			return null;
		}
		String module = modules[curIndex];
		for (PermissionVo sysPermission: list) {
			String permissionName = sysPermission.getName();
			if (permissionName.contains(mark)) {
				permissionName = permissionName.replace(mark,""); //去除一级节点标示
			}
			if (module.equals(permissionName)) {
				if (curIndex == index) {
					return  sysPermission;
				} else {
					List<PermissionVo> childrens = sysPermission.getChildrens();
					if (childrens != null) {
						return getParent(childrens,modules,curIndex+1,index) ;
					}
				}
			}
		}
		return  null;
	}




	public static String arrAToString(String [] values) {
    	if(values!=null && values.length>0) {
    		return values[0];
    	}
    	return "";
    }

    public static String endToLike(String url) {
    	if(url.contains("{")) {
    		int index  = url.indexOf("{");
    		return url.substring(0, index)+"**";
    	}
//    	if(url.contains("/{")) {
//    		int index  = url.indexOf("/{");
//    		return url.substring(0, index);
//    	}
    	return url;
    }

    public static String getModule(String business) {
    	if(business.contains("-")) {
    		String [] arrA = business.split("-");
    		return arrA[0];
    	}
    	return null;
    }

    public static String[] getModules(String business) {
    	if(business.contains("-")) {
    		String [] arrA = business.split("-");
    		return arrA;
    	}
    	return null;
    }
    
    public static String getName(String name) {
    	if(name.contains("-")) {
    		String [] arrA = name.split("-");
    		if(arrA.length==3) {
    			return arrA[1]+"-"+arrA[2];
    		}
    	}
    	return name;
    }

	public static String getLastName(String name) {
		if(name.contains("-")) {
			String [] arrA = name.split("-");
			return arrA[arrA.length-1];
		}
		return name;
	}

}
