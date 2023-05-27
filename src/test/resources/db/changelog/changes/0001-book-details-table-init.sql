CREATE TABLE book_details(
    id BIGINT PRIMARY KEY,
    author VARCHAR(255),
    genre VARCHAR(255),
    isbn TEXT,
    language VARCHAR(255),
    number_of_pages INT,
    publisher VARCHAR(255),
    title VARCHAR(255)
)