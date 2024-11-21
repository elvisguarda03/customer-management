package com.ecommerce.customer.client.request;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerRequest {
    private Long id;
    private String fullName;
    private String contactEmail;
    private String primaryPhone;
    private String location;
}
