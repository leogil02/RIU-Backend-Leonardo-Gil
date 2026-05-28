CREATE INDEX idx_hotel_searches_match
    ON hotel_searches (hotel_id, check_in, check_out, ages)