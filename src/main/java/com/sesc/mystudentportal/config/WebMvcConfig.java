package com.sesc.mystudentportal.config;


import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
@PropertySource("")
public class WebMvcConfig extends WebMvcAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(RequestContextListener.class)
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }


    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }



}
