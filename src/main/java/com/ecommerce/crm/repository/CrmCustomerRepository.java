package com.ecommerce.crm.repository;

import com.ecommerce.crm.entities.CrmCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public interface CrmCustomerRepository extends JpaRepository<CrmCustomer, Long> {
}
