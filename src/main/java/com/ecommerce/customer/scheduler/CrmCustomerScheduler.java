package com.ecommerce.customer.scheduler;

import com.ecommerce.customer.client.CRMCustomerClient;
import com.ecommerce.customer.entities.Customer;
import com.ecommerce.customer.entities.FailedRequest;
import com.ecommerce.customer.enums.EventType;
import com.ecommerce.customer.mapper.CustomerMapper;
import com.ecommerce.customer.repository.CustomerRepository;
import com.ecommerce.customer.repository.FailedRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrmCustomerScheduler {

    private final FailedRequestRepository failedRequestRepository;
    private final CustomerRepository customerRepository;
    private final CRMCustomerClient crmCustomerClient;

    @Scheduled(fixedRate = 60000)
    public void processFailedRequests() {
        List<FailedRequest> failedRequests = failedRequestRepository.findAllByUpdatedAtIsNull();
        if (CollectionUtils.isEmpty(failedRequests)) {
            return;
        }

        String customersId = failedRequests.stream()
                .map(failedRequest -> String.valueOf(failedRequest.getCustomerId()))
                .collect(Collectors.joining(", "));

        log.info("Processing failed requests to the following customersId [{}]", customersId);
        List<Customer> failedCustomers = customerRepository.findAllById(failedRequests.stream()
                .mapToLong(FailedRequest::getCustomerId)
                .boxed()
                .collect(Collectors.toList()));

        Map<Long, FailedRequest> failedRequestMap = failedRequests.stream()
                .collect(Collectors.toMap(FailedRequest::getCustomerId, failedRequest -> failedRequest));
        failedCustomers.parallelStream().forEach(customerToSend -> {
            try {
                FailedRequest failedRequest = failedRequestMap.get(customerToSend.getCustomerId());
                if (Objects.equals(EventType.CREATE, failedRequest.getEventType())) {
                    crmCustomerClient.createCustomer(CustomerMapper.toCrmCustomer(customerToSend));
                } else if (Objects.equals(EventType.UPDATE, failedRequest.getEventType())) {
                    crmCustomerClient.updateCustomer(customerToSend.getCustomerId(), CustomerMapper.toCrmCustomer(customerToSend));
                }

                failedRequest.setUpdatedAt(LocalDate.now());
                failedRequestRepository.save(failedRequest);
            } catch (Exception e) {
                log.debug("Failed to resend request for customer: {}", customerToSend.getCustomerId());
            }
        });
    }
}
