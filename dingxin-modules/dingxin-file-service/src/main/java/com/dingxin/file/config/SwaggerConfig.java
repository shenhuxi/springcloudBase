package com.dingxin.file.config;

import com.dingxin.common.config.swagger.SwaggerBaseConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends SwaggerBaseConfig {

    @Bean
    public Docket createRestApi() {
        ApiInfo api = new ApiInfoBuilder()
                .title("文件上传服务api")
                .description("文件上传服务接口文档说明")
                .version("1.0")
                .build();
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(api)
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.dingxin.micro.auth.control"))
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
//                .securitySchemes(securitySchemes())
//                .securityContexts(securityContexts());
    }


}
