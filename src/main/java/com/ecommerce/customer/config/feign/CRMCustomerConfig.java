package com.ecommerce.customer.config.feign;

import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CRMCustomerConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new CRMCustomerFeignDecoder();
    }

    @Bean
    public Retryer feignErrorRetryer() {
        return new FeignRetryer();
    }
}
