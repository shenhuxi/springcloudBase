package com.dingxin.file.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取文件服务器配置
 * @author shixh
 *
 */ 
@Component
@ConfigurationProperties(prefix="file")
public class FileProperties {
	 
    private String previewUrl; //映射静态文件地址域名

    private String basePath;   //公共存放目录	
    
    private String imageTypes; //图片允许上传类型
    
    private int imageNum;	   //图片允许上传数量
    
    private String fileTypes;  //文件允许上传数量
    
    private int fileNum;


	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public String getImageTypes() {
		return imageTypes;
	}

	public void setImageTypes(String imageTypes) {
		this.imageTypes = imageTypes;
	}

	public int getImageNum() {
		return imageNum;
	}

	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}

	public String getFileTypes() {
		return fileTypes;
	}

	public void setFileTypes(String fileTypes) {
		this.fileTypes = fileTypes;
	}

	public int getFileNum() {
		return fileNum;
	}

	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	
	
}
