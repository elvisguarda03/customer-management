package com.ecommerce.customer.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;

import java.net.URI;

public class EcommerceException extends ErrorResponseException {
    public EcommerceException(String message, HttpStatusCode status, String title) {
        super(status, asProblemDetail(message, title, status), null);
    }

    private static ProblemDetail asProblemDetail(String message, String title, HttpStatusCode status) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle(title);
        problemDetail.setType(URI.create("https://www.wiki.ecommerce.cloud.io/display/weekly/Status+Codes"));
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        return problemDetail;
    }
}
