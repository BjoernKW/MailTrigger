package com.bjoernkw.mailtrigger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("MailTrigger API")
                                .version("v0.0.2")
                                .license(new License()
                                        .name("Apache License, Version 2.0")
                                        .url("https://www.apache.org/licenses/LICENSE-2.0")
                                )
                );
    }
}
