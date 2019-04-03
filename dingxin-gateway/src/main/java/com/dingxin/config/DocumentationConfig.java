package com.dingxin.config;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Primary
public class DocumentationConfig implements SwaggerResourcesProvider {
//    /**
//     * 描述: 手动添加
//     * 作者: qinzhw
//     * 创建时间: 2018/6/5 15:21
//     */
//    @Override
//    public List<SwaggerResource> get() {
//        List resources = new ArrayList<>();
//        resources.add(swaggerResource("系统管理", "/clue/v2/api-docs", "2.0"));
//        return resources;
//    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }


    private final RouteLocator routeLocator;
    public DocumentationConfig(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }
    /**
     * 描述: 自动扫描
     * 作者: qinzhw
     * 创建时间: 2018/6/5 15:21
     */
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList<>();
        List<Route> routes = routeLocator.getRoutes();
        System.out.println(Arrays.toString(routes.toArray()));
        routes.forEach(route -> {
            resources.add(swaggerResource(route.getId(), route.getFullPath().replace("**", "v2/api-docs"),"2.0"));
        });
        resources.add(swaggerResource("网关api", "/v2/api-docs", "2.0"));
        return resources;
    }


}