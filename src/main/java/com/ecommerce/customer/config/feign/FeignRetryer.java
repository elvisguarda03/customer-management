package com.ecommerce.customer.config.feign;

import com.ecommerce.customer.exceptions.GatewayTimeoutException;
import com.ecommerce.customer.exceptions.ServiceUnavailableException;
import feign.RetryableException;
import feign.Retryer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FeignRetryer extends Retryer.Default {

    public FeignRetryer() {
        super(100, TimeUnit.SECONDS.toMillis(1), 3);
    }

    @Override
    public void continueOrPropagate(RetryableException e) {
        log.error("It occurred a problem to consulting API", e);

        if (e.getCause() instanceof SocketTimeoutException) {
            throw new GatewayTimeoutException("Time limit exceeded");
        }
        if (e.getCause() instanceof IOException) {
            throw new ServiceUnavailableException("Service temporarily unavailable");
        }
        super.continueOrPropagate(e);
    }

    @Override
    public Retryer clone() {
        return this;
    }
}
