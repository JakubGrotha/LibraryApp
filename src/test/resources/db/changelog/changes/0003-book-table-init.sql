CREATE TABLE book (
    id BIGINT PRIMARY KEY,
    barcode VARCHAR(255),
    is_available BIT,
    book_details_id BIGINT,
    loan_id BIGINT
)