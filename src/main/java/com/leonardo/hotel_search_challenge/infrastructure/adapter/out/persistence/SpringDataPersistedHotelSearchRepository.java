package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface SpringDataPersistedHotelSearchRepository extends JpaRepository<PersistedHotelSearchEntity, UUID> {

    long countByHotelIdAndCheckInAndCheckOutAndAges(
            String hotelId,
            LocalDate checkIn,
            LocalDate checkOut,
            String ages
    );

}
