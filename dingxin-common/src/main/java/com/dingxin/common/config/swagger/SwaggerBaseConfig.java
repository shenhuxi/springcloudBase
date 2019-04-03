package com.dingxin.common.config.swagger;

import com.dingxin.common.util.StringUtils;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.swagger.web.UiConfiguration;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public class SwaggerBaseConfig {


/*    @Bean
    public Docket createRestApi() {
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization")
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("","","",""))
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //.apis(RequestHandlerSelectors.basePackage("com.dingxin"))
                .paths(PathSelectors.any())
                .build();
    }*/

    private ApiInfo apiInfo(String title,String description, String termsofserviceurl, String version) {
        return new ApiInfoBuilder()
                .title(StringUtils.isBlank(title)?"鼎信公司通用API":title)
                .description(StringUtils.isBlank(description)?"鼎信公司通用接口文档说明":description)
                .termsOfServiceUrl(StringUtils.isBlank(termsofserviceurl)?"http://baidu.com":termsofserviceurl)
                .version(StringUtils.isBlank(version)?"1.0":version)
                .build();
    }

    @Bean
    UiConfiguration uiConfig() {
        return new UiConfiguration(
                "validatorUrl",// url
                "none",       // docExpansion          => none | list
                "alpha",      // apiSorter             => alpha
                "schema",     // defaultModelRendering => schema
                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
                true,        // enableJsonEditor      => true | false
                true);        // showRequestHeaders    => true | false
    }

    /**
     * 描述: 配置header认证 若前端已手动添加header会失效
     * 作者: qinzhw
     * 创建时间: 2018/6/11 16:32
     */
    public  List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    public List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }

}