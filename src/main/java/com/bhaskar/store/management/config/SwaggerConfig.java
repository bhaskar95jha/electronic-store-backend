package com.bhaskar.store.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collection;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.apiInfo(getApiInfo());
        return docket;
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
