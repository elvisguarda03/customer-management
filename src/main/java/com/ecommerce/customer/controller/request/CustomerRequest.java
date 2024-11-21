package com.ecommerce.customer.controller.request;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record CustomerRequest(
        @Nullable
        Long id,
        @NotBlank(message = "firstName cannot be null or empty")
        String firstName,
        @NotBlank(message = "lastName cannot be null or empty")
        String lastName,
        @NotBlank(message = "email cannot be null or empty")
        String email,
        @NotBlank(message = "phoneNumber cannot be null or empty")
        String phoneNumber,
        @Valid
        AddressRequest address
) {
}
