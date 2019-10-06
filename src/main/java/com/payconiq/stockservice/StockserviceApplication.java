package com.payconiq.stockservice;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class StockserviceApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(StockserviceApplication.class, args);
    }


    @Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage(getClass().getPackage().getName()))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo());
    }


    private ApiInfo apiInfo()
    {
        return new ApiInfo(
            "Stock Service API",
            "API for gathering information regards stocks.",
            "API TOS",
            "Terms of service",
            new Contact("Luiz Felipe Chiaradia", "www.github.com/chiaradia", "luiz.fcc@gmail.com"),
            "License of API", "API license URL", Collections.emptyList());
    }

}
