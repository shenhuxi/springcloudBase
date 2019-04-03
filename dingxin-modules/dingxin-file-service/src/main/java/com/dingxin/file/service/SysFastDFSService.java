package com.dingxin.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.csource.common.MyException;
import org.csource.fastdfs.StorageClient1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.dingxin.common.util.FileUtil;
import com.dingxin.common.vo.system.SysUserVo;
import com.dingxin.file.entity.SysFile;
import com.dingxin.file.fastdfs.FastDFSClient;
import com.dingxin.file.fastdfs.FastDFSFile;
import com.dingxin.file.repository.SysFileRepository;

@Service
@Transactional
public class SysFastDFSService {
	
	@Autowired
	private SysFileRepository sysFileRepository;

	private static Logger logger = LoggerFactory.getLogger(SysFastDFSService.class);

	/**
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public SysFile saveFile(MultipartFile multipartFile, SysUserVo userVo) throws IOException {
		SysFile sysFile = new SysFile();
		String[] fileAbsolutePath = {};
		String fileName = multipartFile.getOriginalFilename();
		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
		byte[] file_buff = null;
		InputStream inputStream = multipartFile.getInputStream();
		if (inputStream != null) {
			int len1 = inputStream.available();
			file_buff = new byte[len1];
			inputStream.read(file_buff);
		}
		inputStream.close();
		FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
		try {
			fileAbsolutePath = FastDFSClient.upload(file); // upload to fastdfs
		} catch (Exception e) {
			logger.error("upload file Exception!", e);
		}
		if (fileAbsolutePath == null) {
			logger.error("upload file failed,please upload again!");
		}
		String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
		sysFile.setContentType(multipartFile.getContentType());
		sysFile.setSize(multipartFile.getSize());
		sysFile.setSuffix(FileUtil.getSuffix(multipartFile));
		sysFile.setFileName(fileName);
		sysFile.setLocalPath(fileAbsolutePath[0] + "/" + fileAbsolutePath[1]);
		sysFile.setPath(path);
		sysFile.setCreateDate(new Date());
		sysFile.setCreateUserId(userVo.getId());
		sysFile.setCreateUserName(userVo.getUserName());
		return sysFileRepository.save(sysFile);
	}

	/**
	 * @param multipartFile
	 * @return
	 * @throws IOException
	 */
	public List<SysFile> saveFiles(MultipartFile[] multipartFiles, SysUserVo userVo) throws IOException {
		List<SysFile> list = new ArrayList<SysFile>();
		for (MultipartFile multipartFile : multipartFiles) {
			String[] fileAbsolutePath = {};
			String fileName = multipartFile.getOriginalFilename();
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
			byte[] file_buff = null;
			InputStream inputStream = multipartFile.getInputStream();
			if (inputStream != null) {
				int len1 = inputStream.available();
				file_buff = new byte[len1];
				inputStream.read(file_buff);
			}
			inputStream.close();
			FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
			try {
				fileAbsolutePath = FastDFSClient.upload(file); // upload to fastdfs
			} catch (Exception e) {
				logger.error("upload file Exception!", e);
			}
			if (fileAbsolutePath == null) {
				logger.error("upload file failed,please upload again!");
			}
			String path = FastDFSClient.getTrackerUrl() + fileAbsolutePath[0] + "/" + fileAbsolutePath[1];
			SysFile sysFile = new SysFile();
			sysFile.setContentType(multipartFile.getContentType());
			sysFile.setSize(multipartFile.getSize());
			sysFile.setSuffix(FileUtil.getSuffix(multipartFile));
			sysFile.setFileName(fileName);
			sysFile.setLocalPath(fileAbsolutePath[0] + "/" + fileAbsolutePath[1]);
			sysFile.setPath(path);
			sysFile.setCreateDate(new Date());
			sysFile.setCreateUserId(userVo.getId());
			sysFile.setCreateUserName(userVo.getUserName());
			list.add(sysFile);
		}
		return sysFileRepository.save(list);
	}
	
	/** 
     * 删除文件 
     * @param group 组名 如：group1 
     * @param storagePath 不带组名的路径名称 如：M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg 
     * @return -1失败,0成功 
     */  
    public Integer delete_file(String group ,String storagePath){  
        int result=-1;  
        StorageClient1 storageClient = new StorageClient1();
        try {  
            result = storageClient.delete_file(group, storagePath);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (MyException e) {  
            e.printStackTrace();  
        }  
         return  result;  
    }  
    
    /** 
     *  
     * @param storagePath  文件的全部路径 如：group1/M00/00/00/wKgRsVjtwpSAXGwkAAAweEAzRjw471.jpg 
     * @return -1失败,0成功 
     * @throws IOException 
     * @throws Exception 
     */  
    public Integer delete_file(String storagePath){  
        int result=-1;  
        StorageClient1 storageClient = new StorageClient1();
        try {  
            result = storageClient.delete_file1(storagePath);  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (MyException e) {  
            e.printStackTrace();  
        }  
        return  result;  
    }  
}
