package com.ry.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ry.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("戴瑞雨", "http://localhost:8080", "1552991515@qq.com");
        return new ApiInfoBuilder()
                .title("博客接口文档")
                .description("博客前台相关接口文档")
                .contact(contact)   // 联系方式
                .version("1.0.0")  // 版本
                .build();
    }
}