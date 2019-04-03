package com.dingxin.common.util;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件 工具类
 * @author shixh
 */
public class FileUtil {
	
	public static String getSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}
	
	public static String getSuffix(File file) {
		return getSuffix(file.getName());
	}
	
	public static String getSuffix(MultipartFile file) {
		return getSuffix(file.getOriginalFilename());
	}
	
}
