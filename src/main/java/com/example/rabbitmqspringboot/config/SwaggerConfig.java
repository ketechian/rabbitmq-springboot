package com.example.rabbitmqspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;


/**
 * @Description
 * @Author: HZY
 * @CreateTime: 2021/11/25 10:10
 */

@EnableOpenApi
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                //开关,测试环境开启,上线关闭
                .enable(true)
                .select()
                //根据包路径筛选
                .apis(RequestHandlerSelectors.basePackage("com.example.rabbitmqspringboot.controller"))
                //根据请求筛选
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Swagger3文档",
                "SpringBoot 2.x Api Documentation",
                "3.0",
                "urn:tos",
                new Contact("hzy","",""),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }
}
