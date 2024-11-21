package com.ecommerce.customer.exceptions;

import org.springframework.http.HttpStatus;

public class InternalServerErrorException extends EcommerceException{
    public InternalServerErrorException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}
