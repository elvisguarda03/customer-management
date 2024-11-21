# E-Commerce Customer and CRM Integration Microservice
This project provides a robust solution for integrating customer data between an internal e-commerce system and a third-party CRM. It includes a REST API for managing customer data and demonstrates how to handle third-party integration failures. Additionally, an alternative solution using Kafka instead of HTTP calls for enhanced scalability and fault tolerance is also highlighted.

# Architecture
## Components
## Customer Microservice:

Handles internal customer data through REST API endpoints.
Sends data to the CRM system when customers are created or updated.
## CRM Mock Service:

Simulates the behavior of an external CRM system.
Receives customer updates from the microservice.

## Current Approach (HTTP-Based Integration)
When a customer is created or updated, the system makes an HTTP call to the CRM.
If the CRM is unavailable:
Failed requests are stored in a FailedRequest table.
A Spring Scheduler retries these requests at fixed intervals.

## Kafka-Based Alternative (Enhanced Solution)
1. #### Replace direct HTTP calls with Kafka:
   * Publish customer data changes to a Kafka topic (e.g., customer-events).
   * Each event includes details such as the eventType (CREATE/UPDATE) and the customer data.
2. #### CRM microservice consumes messages from the topic:
   * Processes customer events asynchronously.
   * Handles retries internally without relying on the e-commerce service.

### Why Use Kafka?
* Resilience: Decouples the e-commerce service from the CRM, reducing direct dependencies.
* Scalability: Kafka can handle large volumes of customer data efficiently.
* Fault Tolerance: Kafka ensures at-least-once delivery, making it reliable for critical data synchronization.
* Asynchronous Communication: CRM updates do not block the main application flow.

## Retry Mechanism:
Stores failed integration requests in the database (FailedRequest table).
Scheduled task processes these failed requests periodically.

## Resolution of Key Issues
### Problem: Handling CRM Downtime
#### Retry Scheduler
Uses Spring’s @Scheduled annotation to process failed CRM integration requests every minute.
Retries are grouped by customer ID and processed in parallel.

Failed requests are saved in the FailedRequest table with details about the operation (CREATE or UPDATE).
A scheduler processes these failed requests and attempts to resend them to the CRM.

### Data Mapping Differences
Created a CustomerMapper utility to convert between internal Customer and CRM’s CrmCustomerRequest.
Address fields are concatenated into a single string for the CRM model.


Endpoints
### Internal Customer API
    * POST	/api/customers - Create a new customer
    * GET	/api/customers/{id} - Retrieve customer by ID
    * PUT	/api/customers/{id} - Update an existing customer
    * DELETE	/api/customers/{id}	- Delete a customer
### CRM API
    * POST	/api/crm/customers - Create a CRM customer
    * PUT	/api/crm/customers/{id} - Update a CRM customer

## Pre-requisites
1. #### Java Development Kit
   * Java Development Kit (JDK): A JDK must be installed and configured on your machine to run this application. Download JDK.
   * Minimum Version: JDK 23 (or the version specified in the pom.xml).
2. #### Apache Maven
   * Used to manage project dependencies and build the application.

## Usage
1.  #### Run the Application:
* Start the Spring Boot application.
* Ensure the database is configured for H2 in-memory or your preferred RDBMS.
2. #### Interact with the API:
* Use tools like Postman or cURL to test the REST endpoints.
3. #### Monitor Failed Requests:
* Check the FailedRequest table for entries.
* Ensure the scheduler retries failed requests.
4. #### Simulate CRM Downtime:
* Temporarily disable the CRM mock service.
* Observe the retry mechanism and database updates.


## Conclusion
This project offers a robust customer-CRM integration solution with the flexibility to handle failures. While the implemented approach uses a database and scheduler for retrying failed HTTP requests, the Kafka-based alternative provides a more resilient and scalable architecture for handling data synchronization. By leveraging Kafka's messaging capabilities, businesses can ensure high availability and efficiency in their systems.