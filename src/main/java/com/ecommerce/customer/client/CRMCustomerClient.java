package com.ecommerce.customer.client;


import com.ecommerce.customer.client.request.CrmCustomerRequest;
import com.ecommerce.customer.config.feign.CRMCustomerConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "crmCustomerClient",
        url = "${app.crm-customer.url}",
        configuration = CRMCustomerConfig.class,
        path = "/api/crm/customers")
public interface CRMCustomerClient {

    @PostMapping(consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> createCustomer(@RequestBody CrmCustomerRequest customer);

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody CrmCustomerRequest request);
}