package com.ecommerce.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class GatewayTimeoutException extends EcommerceException{
    public GatewayTimeoutException(String message) {
        super(message, HttpStatus.GATEWAY_TIMEOUT, "Time limit exceeded");
    }
}
