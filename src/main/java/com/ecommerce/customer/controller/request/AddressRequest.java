package com.ecommerce.customer.controller.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "street cannot be null or empty")
        String street,
        @NotBlank(message = "city cannot be null or empty")
        String city,
        @NotBlank(message = "state cannot be null or empty")
        String state,
        @NotBlank(message = "zip cannot be null or empty")
        String zip
) {
}
