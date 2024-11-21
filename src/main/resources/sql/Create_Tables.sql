CREATE TABLE address (
    id BIGINT NOT NULL AUTO_INCREMENT,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    zip_code VARCHAR(20),
    PRIMARY KEY (id)
);

CREATE TABLE CUSTOMER (
    customer_id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(150),
    phone_number VARCHAR(20),
    address_id BIGINT,
    PRIMARY KEY (customer_id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
