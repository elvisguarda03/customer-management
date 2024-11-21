package com.ecommerce.customer.repository;

import com.ecommerce.customer.entities.FailedRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailedRequestRepository extends JpaRepository<FailedRequest, Long> {

    List<FailedRequest> findAllByUpdatedAtIsNull();
}
