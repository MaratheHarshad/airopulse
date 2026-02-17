package com.app.airopulse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class LLMConfig {

    @Bean
    public RestTemplate restTemplate() {

        var factory = new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);   // 2 seconds
        factory.setReadTimeout(3000);      // 3 seconds

        return new RestTemplate(factory);
    }
}
