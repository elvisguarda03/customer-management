package com.ecommerce.customer.service;

import com.ecommerce.customer.client.request.CrmCustomerRequest;
import com.ecommerce.customer.entities.Address;
import com.ecommerce.customer.entities.Customer;
import com.ecommerce.customer.entities.FailedRequest;
import com.ecommerce.customer.enums.EventType;
import com.ecommerce.customer.exceptions.NotFoundException;
import com.ecommerce.customer.mapper.CustomerMapper;
import com.ecommerce.customer.repository.CustomerRepository;
import com.ecommerce.customer.client.CRMCustomerClient;
import com.ecommerce.customer.repository.FailedRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CRMCustomerClient cRMCustomerClientCustomerClient;
    private final FailedRequestRepository failedRequestRepository;

    public Customer createCustomer(Customer customer) {
        log.info("Creating Customer: {}", customer.getFirstName());
        Customer savedCustomer = customerRepository.save(customer);
        callCRMCustomer(savedCustomer, EventType.CREATE);

        log.info("Customer created: {}", savedCustomer.getCustomerId());
        return savedCustomer;
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        log.info("Updating customer with id {}", id);
        Customer customer = findCustomerById(id);
        customer.setFirstName(customerDetails.getFirstName());
        customer.setLastName(customerDetails.getLastName());
        customer.setEmail(customerDetails.getEmail());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());

        Address address = customer.getAddress();
        address.setStreet(customerDetails.getAddress().getStreet());
        address.setCity(customerDetails.getAddress().getCity());
        address.setState(customerDetails.getAddress().getState());
        address.setZipCode(customerDetails.getAddress().getZipCode());
        
        Customer updatedCustomer = customerRepository.save(customer);
        callCRMCustomer(updatedCustomer, EventType.UPDATE);

        return updatedCustomer;
    }

    public void deleteCustomer(Long id) {
        Customer customer = findCustomerById(id);
        customerRepository.delete(customer);
    }

    private void callCRMCustomer(Customer customer, EventType eventType) {
        log.info("Calling CRM customer with id {}", customer.getCustomerId());
        CrmCustomerRequest request = CustomerMapper.toCrmCustomer(customer);
        try {
            CompletableFuture.runAsync(() -> {
                if (Objects.equals(eventType, EventType.CREATE)) {
                    cRMCustomerClientCustomerClient.createCustomer(request);
                } else if (Objects.equals(eventType, EventType.UPDATE)) {
                    cRMCustomerClientCustomerClient.updateCustomer(request.getId(), request);
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage());
            saveFailedRequest(customer.getCustomerId(), eventType);
        }
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found with id " + id));
    }

    private void saveFailedRequest(Long customerId, EventType eventType) {
        FailedRequest failedRequest = FailedRequest.builder()
                .customerId(customerId)
                .createdAt(LocalDate.now())
                .eventType(eventType)
                .build();

        failedRequestRepository.save(failedRequest);
        log.info("Failed request for customer {} stored in the database in order to send again to update the CRM: ", customerId);
    }
}
