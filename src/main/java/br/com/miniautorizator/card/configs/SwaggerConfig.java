package br.com.miniautorizator.card.configs;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("")
                .packagesToScan("br.com.miniautorizator.card.configs")
                .pathsToMatch("/**")
                .build();
    }
}
