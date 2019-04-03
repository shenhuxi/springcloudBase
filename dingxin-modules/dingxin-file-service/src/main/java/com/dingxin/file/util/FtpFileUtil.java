package com.dingxin.file.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dingxin.file.config.FtpProperties;



/**
 * ftp-util
 * @author shixh
 *
 */
public class FtpFileUtil {
 
	private  final static Logger logger = LoggerFactory.getLogger(FtpFileUtil.class);
	
	public static boolean sendFileByFtp(FtpProperties ftpProperties,FileInputStream in,String sendPath,String sendName){
		FTPClient ftp = new FTPClient();
		int replyCode;
		try {
			ftp.connect(ftpProperties.getHost(),ftpProperties.getPort());// 连接FTP服务器
            ftp.login(ftpProperties.getUserName(),ftpProperties.getPassWord());// 登录
			replyCode = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(replyCode)) {
				System.out.println("FTP登录失败");
				return false;
			}
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode(); // 设置被动模式
			ftp.makeDirectory(ftpProperties.getBasePath());
			ftp.makeDirectory(sendPath);
			ftp.changeWorkingDirectory(sendPath);
			boolean result = ftp.storeFile(sendName, in);
			return result;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(ftp.isConnected()){
				try {
					ftp.disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
    /**
     * @param fileName
     * @param input
     * @param ftpProperties
     * @param path  文件类型目录:images/file/zip/video 
     * @param module
     * @return
     */
    public static boolean uploadFile(String fileName,InputStream input,FtpProperties ftpProperties,String type,String module){
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(ftpProperties.getHost(),ftpProperties.getPort());// 连接FTP服务器
            ftp.login(ftpProperties.getUserName(),ftpProperties.getPassWord());// 登录
            ftp.setBufferSize(1024*1024);  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //结构:文件存放目录(commonPath)/文件类型(type)/模块(module)
            String filePath = ftpProperties.getBasePath().concat(type).concat(File.separator).concat(module).concat(File.separator);
            boolean success = ftp.makeDirectory(filePath);
            logger.info("mkdir filePath start:mkdiring"+filePath+".....");
            ftp.changeWorkingDirectory(filePath);
            ftp.enterLocalPassiveMode();
            ftp.storeFile(fileName,input);
            logger.info("mkdir filePath success :"+success);
            input.close();
            ftp.logout();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return true;
    }
    
    
    /**
     * test
     * @param originFileName
     * @param input
     * @return
     */
    public static boolean uploadFile(String originFileName,InputStream input){
        String FTP_ADDRESS = "127.0.0.1";
        int FTP_PORT = 21;
        String FTP_USERNAME = "dingxin";
        String FTP_PASSWORD = "dingxin123456";
        String FTP_BASEPATH = "E:\\file_dingxin";
    	boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("GBK");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.setBufferSize(1024*1024);  
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return success;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.changeWorkingDirectory(FTP_BASEPATH);
            ftp.storeFile(originFileName,input);
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                }
            }
        }
        return success;
    }

}


