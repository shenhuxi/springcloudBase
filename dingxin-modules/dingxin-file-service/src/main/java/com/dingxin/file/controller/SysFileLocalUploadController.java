package com.dingxin.file.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.dingxin.common.annotation.UserOperate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.file.SysFileVo;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.file.config.FileProperties;
import com.dingxin.file.constant.FileConstant;
import com.dingxin.file.entity.SysFile;
import com.dingxin.file.service.SysFileService;

/**
 * 图片上传(上传到本地)
 * 文件存放目录(commonPath)/文件类型(type)/模块(module)
 * @author shixh
 *
 */
@RestController
@RequestMapping("/local")
public class SysFileLocalUploadController  extends BaseController{
	
	@Autowired
	private FileProperties fileProperties;

	@Autowired
	private SysFileService sysFileService;


	/**
	 * 多图片上传(上传到本地)
	 * @param files
	 * @param module 功能模块名,例如：module=user,则保存在{path}/images/user目录下
	 * @param business 业务模块描述,如"用户管理-头像上传 "
	 * @return
	 * @throws Exception
	 */
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-图片上传-多图片上传到本地")
	@PostMapping(value = "/imageUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResultObject imageUpload(@NotNull@RequestParam("files") MultipartFile [] files,
			@RequestParam(value="module",defaultValue=FileConstant.COMMON_PATH) String module,
			@RequestParam(value="business",defaultValue="文件上传")String business) throws Exception {

			if(files==null || files.length==0) {
                return ResultObject.fail("请选择附件上传!");
            }

			if(files.length>fileProperties.getImageNum()) {
				return ResultObject.fail("最多可上传"+fileProperties.getImageNum()+"个图片");
			}

			for(MultipartFile file:files) {
				String originalFilename = file.getOriginalFilename();
				String ext = StringUtils.substringAfter(originalFilename, ".");
				if(!fileProperties.getImageTypes().toUpperCase().contains(ext.toUpperCase())) {
					return ResultObject.fail("请选择符合条件的格式上传!",originalFilename+"格式有限制.");
				}
			}

		try {
			request.setCharacterEncoding("UTF-8");
			List<SysFileVo> sysFileVos = new ArrayList<SysFileVo>();
			for(MultipartFile file:files) {
				String originalFilename = file.getOriginalFilename();
				String ext = StringUtils.substringAfter(originalFilename, ".");
				String fileName = String.valueOf(System.currentTimeMillis()) + "." + ext;
				String basePath = fileProperties.getBasePath()+File.separator;
				String filePath = FileConstant.IMAGES_PATH.concat(File.separator).concat(module).concat(File.separator);
				logger.info("filePath:"+filePath);
				File newFile = new File(basePath+filePath,fileName);
				if (!newFile.getParentFile().exists()) {
					logger.info("mkdir filePath start:mkdiring"+filePath+".....");
					boolean success = newFile.getParentFile().mkdirs();
					logger.info("mkdir filePath success :"+success);
				}
				file.transferTo(newFile);
				// 将图片流转换进行BASE64加码
				// BASE64Encoder encoder = new BASE64Encoder();
				// String data = encoder.encode(file.getBytes());
				String data = filePath.replaceAll("\\\\", "/") + fileName;
				logger.info("mkdir path :"+data);
				SysUserVo sysUser = this.getLoginUser();
				if(sysUser==null) {
                    return ResultObject.fail("用户信息为空 ，请先登录 ！");
                }
				SysFile sysFile = sysFileService.saveSysFile(sysUser,file,data);
				if(sysFile!=null) {
					sysFileVos.add(new SysFileVo(sysFile.getId(),sysFile.getPath(),sysFile.getContentType(),sysFile.getFileName(),fileProperties.getPreviewUrl()+sysFile.getPath()));
				}
			}
			return ResultObject.ok("success",sysFileVos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResultObject.ok();
	}

	/**
	 * 多附件上传(上传到本地)
	 * @param files
	 * @param module 功能模块名,例如：module=user,则保存在{path}/files/user目录下
	 * @param business 业务模块描述,如"用户管理-头像上传 "
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/fileUpload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-附件上传-多附件上传到本地")
	public ResultObject fileUpload(@NotNull@RequestParam("files") MultipartFile [] files,
			@RequestParam(value="module",defaultValue=FileConstant.COMMON_PATH) String module,
			@RequestParam(value="business",defaultValue="文件上传")String business) throws Exception {

			if(files==null || files.length==0) {
                return ResultObject.fail("请选择附件上传!");
            }

			if(files.length>fileProperties.getImageNum()) {
				return ResultObject.fail("最多可上传"+fileProperties.getImageNum()+"个图片");
			}

			for(MultipartFile file:files) {
				String originalFilename = file.getOriginalFilename();
				String ext = StringUtils.substringAfter(originalFilename, ".");
				if(!fileProperties.getFileTypes().toUpperCase().contains(ext.toUpperCase())) {
					return ResultObject.fail("请选择符合条件的格式上传!",originalFilename+"格式有限制.");
				}
			}

		try {
			request.setCharacterEncoding("UTF-8");
			List<SysFileVo> sysFileVos = new ArrayList<SysFileVo>();
			for(MultipartFile file:files) {
				String originalFilename = file.getOriginalFilename();
				String ext = StringUtils.substringAfter(originalFilename, ".");
				String fileName = String.valueOf(System.currentTimeMillis()) + "." + ext;
				String basePath = fileProperties.getBasePath()+File.separator;
				String filePath = FileConstant.FILES_PATH.concat(File.separator).concat(module).concat(File.separator);
				logger.info("filePath:{}",filePath);
				File newFile = new File(basePath+filePath,fileName);
				if (!newFile.getParentFile().exists()) {
					logger.info("mkdir filePath start:mkdiring{}.....",filePath);
					boolean success = newFile.getParentFile().mkdirs();
					logger.info("mkdir filePath success :{}",success);
				}
				file.transferTo(newFile);

				String data = filePath.replaceAll("\\\\", "/") + fileName;
				logger.info("mkdir path :{}",data);
				SysUserVo sysUser = this.getLoginUser();
				if(sysUser==null) {
                    return ResultObject.fail("用户信息为空 ，请先登录 ！");
                }
				SysFile sysFile = sysFileService.saveSysFile(sysUser,file,data);
				if(sysFile!=null) {
					sysFileVos.add(new SysFileVo(sysFile.getId(),sysFile.getPath(),sysFile.getContentType(),sysFile.getFileName(),fileProperties.getPreviewUrl()+sysFile.getPath()));
				}
			}
			return ResultObject.ok("success",sysFileVos);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return ResultObject.ok();
	}

	/**
	 * 根据 图片相对路径删除附件，多个逗号分开；
	 * @param paths
	 * @param business 业务模块描述,如"用户管理-头像删除 "
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/deleteByPaths")
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-删除附件-相对路径删除")
	public ResultObject deleteByPaths(@RequestParam("paths") String paths,
			@RequestParam(value="business",defaultValue="文件删除")String business) throws Exception {

		if(StringUtils.isBlank(paths)) {
            return ResultObject.fail("缺少参数paths!");
        }

		SysUserVo sysUser = this.getLoginUser();
		if(sysUser==null) {
            return ResultObject.fail("用户信息为空 ，请先登录 ！");
        }
		String [] pathsArr = paths.split(",");
		for(String path:pathsArr) {
			SysFile sysFile = sysFileService.findByPath(path);
			if(sysFile!=null) {
				sysFileService.deleteByPath(path);
			}
		}
		return ResultObject.ok("删除成功!");
	}

	/**
	 * 根据 图片ID删除附件，多个逗号分开；
	 * @param ids
	 * @param business 业务模块描述,如"用户管理-头像删除 "
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/deleteByIds")
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-删除图片-图片ID删除")
	public ResultObject deleteByIds(@RequestParam("ids") String ids,
			@RequestParam(value="business",defaultValue="文件删除")String business) throws Exception {

		if(StringUtils.isBlank(ids)) {
            return ResultObject.fail("缺少参数ids!");
        }

		SysUserVo sysUser = this.getLoginUser();
		if(sysUser==null) {
            return ResultObject.fail("用户信息为空 ，请先登录 ！");
        }
		String [] pathsArr = ids.split(",");
		for(String id:pathsArr) {
			SysFile sysFile = sysFileService.findOne(Long.parseLong(id));
			if(sysFile!=null) {
				sysFileService.delete(Long.parseLong(id));
			}
		}
		return ResultObject.ok("删除成功!");
	}

	
}
