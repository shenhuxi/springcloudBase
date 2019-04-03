package com.dingxin.file.controller;


import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dingxin.common.controller.BaseController;
import com.dingxin.common.util.ResultObject;
import com.dingxin.file.config.FtpProperties;
import com.dingxin.file.constant.FileConstant;
import com.dingxin.file.util.FtpFileUtil;

/**
 * 图片上传
 * 文件存放目录(commonPath)/文件类型(type)/模块(module)
 * @author shixh
 *
 */
@RestController
@RequestMapping("/ftp")
public class SysFIleFtpUploadController  extends BaseController{
	
	@Autowired
	private FtpProperties ftpProperties;
	
	/**
	 * ftp-upload 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value="/upload")
    public ResultObject ftpUpload(@RequestParam("file") MultipartFile file,
    		@RequestParam(value="module",defaultValue=FileConstant.COMMON_PATH) String module) throws IOException {
		logger.info("upload image:");
        String fileName = file.getOriginalFilename();
        InputStream inputStream=file.getInputStream();
        String filePath=null;
        Boolean flag=FtpFileUtil.uploadFile(fileName,inputStream,ftpProperties,FileConstant.IMAGES_PATH,module);
        if(flag==true){
            System.out.println("ftp上传成功！");
            filePath=fileName;
        }
        return ResultObject.ok();  //该路径图片名称，前端框架可用ngnix指定的路径+filePath,即可访问到ngnix图片服务器中的图片
    }
	

	
}
