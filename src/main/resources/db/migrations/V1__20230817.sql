CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   varchar(64)          NOT NULL UNIQUE,
    password   varchar(255)         NOT NULL,
    role       varchar(30)          NOT NULL,
    firstName  varchar(64)          NOT NULL,
    lastName   varchar(64)          NOT NULL,
    enable     BOOLEAN DEFAULT TRUE NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);