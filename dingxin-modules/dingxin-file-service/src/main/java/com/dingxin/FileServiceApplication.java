package com.dingxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;



/**
 * 资源管理
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@EnableFeignClients
public class FileServiceApplication {
	/*@Bean  
	public MultipartConfigElement multipartConfigElement() {  
	    MultipartConfigFactory factory = new MultipartConfigFactory();  
	    //文件最大  
	    factory.setMaxFileSize("20MB"); //KB,MB  
	    /// 设置总上传数据总大小  
	    factory.setMaxRequestSize("20MB");  
	    return factory.createMultipartConfig();  
	}  */
    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
