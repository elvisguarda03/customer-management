INSERT INTO ADDRESS (id, street, city, state, zip_code) VALUES
                                                                       (1, '123 Elm Street', 'Springfield', 'Illinois', '62704'),
                                                                       (2, '456 Oak Avenue', 'Metropolis', 'New York', '10118'),
                                                                       (3, '789 Pine Road', 'Gotham', 'New Jersey', '07001');

INSERT INTO CUSTOMER (customer_id, first_name, last_name, email, phone_number, address_id) VALUES
                                                                                   (1, 'John', 'Doe', 'john.doe@example.com', '555-1234', 1),
                                                                                   (2, 'Jane', 'Smith', 'jane.smith@example.com', '555-5678', 2),
                                                                                   (3, 'Bruce', 'Wayne', 'bruce.wayne@example.com', '555-9876', 3);