package com.exalink.hrmsdatabaseapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author ankitkverma
 *
 */
@Configuration
public class RestTemplateClient {
	
	@Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}