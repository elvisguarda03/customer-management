package com.ecommerce.crm.entities;

import com.ecommerce.customer.enums.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "FULL_NAME")
    private String fullName;
    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;
    @Column(name = "PRIMARY_PHONE")
    private String primaryPhone;
    @Column(name = "LOCATION")
    private String location;
    @Column(name = "EVENT_TYPE")
    private EventType eventType;
}