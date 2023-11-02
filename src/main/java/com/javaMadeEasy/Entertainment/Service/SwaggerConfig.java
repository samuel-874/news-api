package com.javaMadeEasy.Entertainment.Service;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;

@Configuration
public class SwaggerConfig {
    public static final Contact DEFAULT_CONTACT = new Contact(
            "Sammie"
            ,"https://twitter.com/Sammy550451712.com",
            "samuelabiodun1205@gmail.com");
    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo(
            "API Title","Awesome Post API ",
            "1.0","urn:tos", DEFAULT_CONTACT,
            "Apache 2.o","http://www.apache.org/licenses/LICENSE-2.0",new ArrayList());

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO);
    }
}
