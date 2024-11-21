package com.ecommerce.customer.handler;

import com.ecommerce.customer.exceptions.EcommerceException;
import com.ecommerce.customer.exceptions.NotFoundException;
import feign.RetryableException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleResourceNotFound(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getBody());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException ex) {
        Optional<ConstraintViolation<?>> register = ex.getConstraintViolations().stream().findFirst();
        if (register.isEmpty()) {
            return ResponseEntity.badRequest().body(getProblemDetail(HttpStatus.BAD_REQUEST, "Invalid parameter"));
        } else {
            ConstraintViolation<?> constraintViolation = register.get();
            return ResponseEntity.badRequest().body(getProblemDetail(HttpStatus.BAD_REQUEST, constraintViolation.getMessage()));
        }
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ProblemDetail> handleRetryableException(RetryableException ex) {
        if (!(ex.getCause() instanceof RetryableException)) {
            if (ex.getCause() instanceof EcommerceException ecommerceException) {
                return this.handleEcommerceException(ecommerceException);
            }
        }

        return this.handleGenericException(ex);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ProblemDetail> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(getProblemDetail(HttpStatus.BAD_REQUEST, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ProblemDetail>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ProblemDetail> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> getProblemDetail(HttpStatus.BAD_REQUEST, error.getDefaultMessage()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(EcommerceException.class)
    public ResponseEntity<ProblemDetail> handleEcommerceException(EcommerceException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(ex.getBody());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(getProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    private static ProblemDetail getProblemDetail(HttpStatusCode status, String message) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, message);
        problemDetail.setTitle("Invalid request body");
        problemDetail.setProperty("timestamp", System.currentTimeMillis());
        return problemDetail;
    }
}
