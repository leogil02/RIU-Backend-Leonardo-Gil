package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface SpringDataPersistedHotelSearchRepository extends JpaRepository<PersistedHotelSearchEntity, UUID> {

    @Query("""
        SELECT COUNT(e) FROM PersistedHotelSearchEntity e
        WHERE e.hotelId=:hotelId
        AND e.checkIn=:checkIn
        AND e.checkOut=:checkOut
        AND e.ages=:ages
    """)
    long countMatching(
            @Param("hotelId") String hotelId,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("ages") String ages
    );

}
