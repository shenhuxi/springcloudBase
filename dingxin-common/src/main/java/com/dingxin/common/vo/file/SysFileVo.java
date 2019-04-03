package com.dingxin.common.vo.file;

public class SysFileVo {
	
	private long id;
	private String path;
	private String contentType;
	private String fileName;
	private String previewUrl;
	
	public SysFileVo(Long id, String path,String contentType,String fileName,String previewUrl) {
		super();
		this.id=id;
		this.path=path;
		this.contentType=contentType;
		this.fileName = fileName;
		this.previewUrl=previewUrl;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPreviewUrl() {
		return previewUrl;
	}

	public void setPreviewUrl(String previewUrl) {
		this.previewUrl = previewUrl;
	}

	
	
}
