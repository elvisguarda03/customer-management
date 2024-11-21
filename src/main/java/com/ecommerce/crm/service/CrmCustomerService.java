package com.ecommerce.crm.service;

import com.ecommerce.crm.entities.CrmCustomer;
import com.ecommerce.crm.repository.CrmCustomerRepository;
import com.ecommerce.customer.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrmCustomerService {
    private final CrmCustomerRepository customerRepository;

    public void createCustomer(CrmCustomer customer) {
        log.info("Creating CRM customer with name {}", customer.getFullName());
        customerRepository.save(customer);
    }

    public void updateCustomer(Long id, CrmCustomer updatedCustomer) {
        log.info("Updating CRM customer with id {}", id);
        CrmCustomer crmCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CRM Customer not found"));
        CrmCustomer updatedCrmCustomer = crmCustomer.toBuilder()
                .contactEmail(updatedCustomer.getContactEmail())
                .fullName(updatedCustomer.getFullName())
                .location(updatedCustomer.getLocation())
                .primaryPhone(updatedCustomer.getPrimaryPhone())
                .build();

        customerRepository.save(updatedCrmCustomer);
    }
}
