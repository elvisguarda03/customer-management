package com.ecommerce.customer.exceptions;

import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends EcommerceException{
    public ServiceUnavailableException(String message) {
        super(message, HttpStatus.GATEWAY_TIMEOUT, "Service unavailable");
    }
}
