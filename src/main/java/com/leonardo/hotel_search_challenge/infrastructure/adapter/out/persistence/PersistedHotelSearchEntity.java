package com.leonardo.hotel_search_challenge.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "hotel_searches")
public class PersistedHotelSearchEntity {

    @Id
    @Column(name = "search_id")
    private UUID searchId;

    @Column(name = "hotel_id", nullable = false)
    private String hotelId;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "ages", nullable = false)
    @Convert(converter = AgeConverter.class)
    private List<Integer> ages;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    protected PersistedHotelSearchEntity(){}

    public PersistedHotelSearchEntity(UUID searchId, String hotelId, LocalDate checkIn, LocalDate checkOut,
                                      List<Integer> ages, Instant occurredAt) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
        this.occurredAt = occurredAt;
    }

    public UUID getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }
}
