package com.dingxin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * http://127.0.0.1:8223/dingxin-db-local.yml 能直接读取到GIT远端的文件
 * http://127.0.0.1:8223/dingxin-db-local.properties
 */
@EnableDiscoveryClient
@EnableConfigServer // 开启配置服务
@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

}
