package com.ecommerce.customer.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends EcommerceException{
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND, "Resource not found");
    }
}
