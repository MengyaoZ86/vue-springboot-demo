package com.example.demo1.config;

import com.example.demo1.service.AdminService;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("vue Demo")
                        .version("1.0")
                        .description("springboot3+vue demo project -zmy")
                        .contact(new Contact().name("Mengyao Zhang").url("https://github.com/MengyaoZ86")));
    }
}
