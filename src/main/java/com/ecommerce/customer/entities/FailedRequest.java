package com.ecommerce.customer.entities;

import com.ecommerce.customer.client.request.CrmCustomerRequest;
import com.ecommerce.customer.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@Table(name = "FAILED_REQUEST")
@NoArgsConstructor
@AllArgsConstructor
public class FailedRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    private EventType eventType;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Setter
    @Column(name = "UPDATED_AT")
    private LocalDate updatedAt;
}
