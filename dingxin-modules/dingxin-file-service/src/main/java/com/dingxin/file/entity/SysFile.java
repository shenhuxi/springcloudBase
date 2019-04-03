package com.dingxin.file.entity;

import javax.persistence.Entity;

import com.dingxin.data.jpa.entity.BaseEntity;

/**
 * 文件
 * @author shixh
 *
 */
@Entity
public class SysFile extends BaseEntity{
	
	private String fileName;
	private String path;
	private String contentType;
	private String suffix;
	/**
	 * 本地路径
	 */
	private String localPath;
	
	private long size;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

}
