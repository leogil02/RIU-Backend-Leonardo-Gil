package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface SpringDataPersistedHotelSearchRepository extends JpaRepository<PersistedHotelSearchEntity, UUID> {

    @Query(value = """
        SELECT COUNT(*) FROM hotel_searches
        WHERE hotel_id=:hotelId
        AND check_in=:checkIn
        AND check_out=:checkOut
        AND ages=:ages
    """, nativeQuery = true)
    long countMatching(
            @Param("hotelId") String hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("ages") String ages
    );

}
