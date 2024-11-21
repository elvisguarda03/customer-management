package com.ecommerce.customer.config.feign;

import com.ecommerce.customer.exceptions.InternalServerErrorException;
import feign.Response;
import feign.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class FeignLoggerUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignLoggerUtils.class.getName());

    private FeignLoggerUtils() {}

    public static void loggingRequestResponseFeign(String methodKey, Response response, String respoonseBodyAsString) {
        String request = new String(getRequestBody(response), StandardCharsets.UTF_8);
        Map<String, Collection<String>> headers = response.request().headers();
        String httpMethod = response.request().httpMethod().name();
        String url = response.request().url();
        Map<String, Collection<String>> responseHeaders = response.headers();
        int status = response.status();

        LOGGER.error("[{}] ---> {} {}", methodKey, httpMethod, url);
        LOGGER.error("Request: {}", request);
        LOGGER.error("Request headers: {}", headers);
        LOGGER.error("[{}] <--- Status {}, Resposta {}\n{}", methodKey, status, httpMethod, respoonseBodyAsString);
        LOGGER.error("Response headers: {}", responseHeaders);
    }

    private static byte[] getRequestBody(Response response) {
        return Objects.requireNonNullElse(response.request().body(), "<request body null>".getBytes(StandardCharsets.UTF_8));
    }

    public static String extractResponseBodyAsString(Response.Body responseBody) {
        try {
            return Objects.requireNonNull(Util.toString(responseBody.asReader(Util.UTF_8)));
        } catch (IOException ex) {
            LOGGER.error("Error while extracting response body as string", ex);
            throw new InternalServerErrorException("Unknown error");
        } catch (Exception ex) {
            return "<response body null>";
        }
    }
}
