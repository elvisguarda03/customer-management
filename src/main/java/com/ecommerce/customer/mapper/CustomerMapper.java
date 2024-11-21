package com.ecommerce.customer.mapper;

import com.ecommerce.crm.entities.CrmCustomer;
import com.ecommerce.customer.client.request.CrmCustomerRequest;
import com.ecommerce.customer.controller.request.AddressRequest;
import com.ecommerce.customer.controller.request.CustomerRequest;
import com.ecommerce.customer.entities.Address;
import com.ecommerce.customer.entities.Customer;

public class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toCustomer(CustomerRequest customerRequest) {
        return Customer.builder()
                .customerId(customerRequest.id())
                .phoneNumber(customerRequest.phoneNumber())
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .address(buildAddress(customerRequest.address()))
                .build();
    }

    private static Address buildAddress(AddressRequest addressRequest) {
        return Address.builder()
                .street(addressRequest.street())
                .city(addressRequest.city())
                .state(addressRequest.state())
                .zipCode(addressRequest.zip())
                .build();
    }

    public static CrmCustomerRequest toCrmCustomer(Customer customer) {
        return CrmCustomerRequest.builder()
                .id(customer.getCustomerId())
                .fullName(customer.getFirstName() + " " + customer.getLastName())
                .contactEmail(customer.getEmail())
                .primaryPhone(customer.getPhoneNumber())
                .location(
                        String.format("%s, %s, %s, %s",
                                customer.getAddress().getStreet(),
                                customer.getAddress().getCity(),
                                customer.getAddress().getState(),
                                customer.getAddress().getZipCode())
                )
                .build();
    }

    public static CrmCustomer toCrmCustomer(CrmCustomerRequest request) {
        return CrmCustomer.builder()
                .id(request.getId())
                .fullName(request.getFullName())
                .contactEmail(request.getContactEmail())
                .primaryPhone(request.getPrimaryPhone())
                .location(request.getLocation())
                .build();
    }
}
