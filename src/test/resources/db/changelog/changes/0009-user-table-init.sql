CREATE TABLE user_table (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255),
    enabled BIT,
    locked BIT,
    name VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255)
);