package com.ecommerce.crm.controller;

import com.ecommerce.crm.service.CrmCustomerService;
import com.ecommerce.customer.client.request.CrmCustomerRequest;
import com.ecommerce.customer.entities.Customer;
import com.ecommerce.customer.mapper.CustomerMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/crm/customers")
public class CrmCustomerController {

    private final CrmCustomerService customerService;

    public CrmCustomerController(CrmCustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody CrmCustomerRequest request) {
        customerService.createCustomer(CustomerMapper.toCrmCustomer(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody CrmCustomerRequest request) {
        customerService.updateCustomer(id, CustomerMapper.toCrmCustomer(request));
        return ResponseEntity.ok().build();
    }
}