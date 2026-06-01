CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT NOT NULL,
                       username VARCHAR(255),
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       role VARCHAR(50) NOT NULL,

                       CONSTRAINT pk_users PRIMARY KEY (id),
                       CONSTRAINT uk_users_email UNIQUE (email)
);
