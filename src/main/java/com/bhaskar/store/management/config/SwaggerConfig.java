package com.bhaskar.store.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());

        docket.securityContexts(Arrays.asList(getSecurityContext()));
        docket.securitySchemes(Arrays.asList(getSecuritySchemes()));


        ApiSelectorBuilder select = docket.select();
        select.apis(RequestHandlerSelectors.any());
        select.paths(PathSelectors.any());
        Docket build = select.build();

        return build;
    }

    private SecurityContext getSecurityContext() {

        AuthorizationScope[] scopes = {new AuthorizationScope("Global","Access Everything")};

        List<SecurityReference> securityReferenceList = new ArrayList<>();
        securityReferenceList.add(new SecurityReference("JWT",scopes));


        SecurityContext context = SecurityContext
                .builder()
                .securityReferences(securityReferenceList)
                .build();

        return context;
    }

    private ApiKey getSecuritySchemes() {
        return new ApiKey("JWT","Authorization","header");
    }

    private ApiInfo getApiInfo() {
        String title = "Store Management Backend";
        String description = "This is a backend Project created for learning spring boot ";
        String version = "1.0.0";
        String termsOfServiceURL = "http://localhost:8085/login";
        Contact contact = new Contact("Bhaskar Jha","","bhaskar.1495jha@gmail.com");
        String license = "copyright@2023:bhaskar";
        String licenseUrl = "";
        Collection<VendorExtension> vendorExtensions = new ArrayList<>();

        ApiInfo apiInfo = new ApiInfo(title,description,version,termsOfServiceURL,contact,license,licenseUrl,vendorExtensions);

        return apiInfo;
    }

    /*
    * public static final Contact DEFAULT_CONTACT = new Contact("", "", "");
    public static final ApiInfo DEFAULT;
    private final String version;
    private final String title;
    private final String description;
    private final String termsOfServiceUrl;
    private final String license;
    private final String licenseUrl;
    private final Contact contact;
    private final List<VendorExtension> vendorExtensions;
    * */
}
