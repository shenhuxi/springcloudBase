package com.dingxin.file.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dingxin.common.annotation.UserOperate;
import com.dingxin.common.constant.OperateConstant;
import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.file.constant.FileConstant;
import com.dingxin.file.entity.SysFile;
import com.dingxin.file.service.SysFastDFSService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/fastdfs")
public class SysFastDFSUploadController extends BaseController {

	@Autowired
	private SysFastDFSService sysFastDFSService;

	/**
	 * 上传单个文件
	 * 
	 * @param
	 * @return
	 */
	@PostMapping("/upload/image")
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-文件上传-单文件上传")
	@ApiOperation(value = "上传单个文件", notes = "")
	@ApiImplicitParam(name = "MultipartFile", value = "文件", required = true, paramType = "body", dataType = "MultipartFile")
	public ResultObject uploadSingle(@RequestParam("files") MultipartFile[] files,
			@RequestParam(value = "module", defaultValue = FileConstant.COMMON_PATH) String module,
			@RequestParam(value = "business", defaultValue = "文件上传") String business, HttpServletRequest request) {
		try {
			SysUserVo userVo = getLoginUser();
			for (MultipartFile file : files) {
				if (!file.getContentType().startsWith("image")) {
					return ResultObject.fail("上传类型为非图片");
				}
			}
			if (files.length == 0) {
				return ResultObject.fail("上传文件为空");
			}
			if (userVo == null) {
				return ResultObject.fail("未登录");
			}
			List<SysFile> list = sysFastDFSService.saveFiles(files, userVo);
			return ResultObject.ok(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultObject.fail("上传文件失败");
		}
	}

	/**
	 * 上传多个文件
	 * 
	 * @param files
	 * @return
	 */
	@PostMapping("/upload/multi")
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-文件上传-多文件上传")
	@ApiOperation(value = "上传多个文件", notes = "")
	@ApiImplicitParam(name = "MultipartFile", value = "文件", required = true, paramType = "body", dataType = "MultipartFile")
	public ResultObject uploadMulti(@RequestParam("files") MultipartFile[] files,
			@RequestParam(value = "module", defaultValue = FileConstant.COMMON_PATH) String module,
			@RequestParam(value = "business", defaultValue = "文件上传") String business, HttpServletRequest request) {
		if (files.length == 0) {
			return ResultObject.fail("上传文件为空");
		}
		try {
			SysUserVo userVo = getLoginUser();
			List<SysFile> list = sysFastDFSService.saveFiles(files, userVo);
			return ResultObject.ok(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultObject.fail("上传文件失败");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResultObject.fail("上传文件失败");
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param paths
	 * @return
	 */
	@UserOperate(name = OperateConstant.SAVE, business = "文件管理-文件上传-文件删除")
	@ApiOperation(value = "删除文件", notes = "")
	@ApiImplicitParam(name = "String[]", value = "路径数组", required = true, paramType = "body", dataType = "String[]")
	@DeleteMapping("/delete/file")
	public ResultObject deleteFile(String[] paths) {
		if (paths.length <= 0) {
			return ResultObject.fail("参数错误");
		}
		for (String path : paths) {
			sysFastDFSService.delete_file(path);
		}
		return ResultObject.ok("删除成功");
	}

}
