CREATE TABLE hotel_searches (
    search_id   RAW(16) NOT NULL,
    hotel_id    VARCHAR2(1020)  NOT NULL,
    check_in    DATE    NOT NULL,
    check_out   DATE NOT NULL,
    ages    VARCHAR2(1020)  NOT NULL,
    occurred_at TIMESTAMP(9) WITH TIME ZONE NOT NULL,
    CONSTRAINT  pk_hotel_searches    PRIMARY KEY (search_id)
);