package com.ecommerce.customer.config.feign;

import com.ecommerce.customer.exceptions.ServiceUnavailableException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import static com.ecommerce.customer.config.feign.FeignLoggerUtils.extractResponseBodyAsString;
import static com.ecommerce.customer.config.feign.FeignLoggerUtils.loggingRequestResponseFeign;

@Slf4j
public class CRMCustomerFeignDecoder implements ErrorDecoder {
    @Override
    public Exception decode(final String methodKey, final Response response) {
        try {
            final String responseBodyAsString = extractResponseBodyAsString(response.body());
            loggingRequestResponseFeign(methodKey, response, responseBodyAsString);
        } catch (final Exception exception) {
            log.error("Error while extracting error message", exception);
            return new ServiceUnavailableException(exception.getMessage());
        }
        return null;
    }
}
