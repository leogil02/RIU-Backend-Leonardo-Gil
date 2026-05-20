package com.leonardo.hotel_search_challenge;

import com.leonardo.hotel_search_challenge.domain.event.HotelSearchedEvent;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearch;
import com.leonardo.hotel_search_challenge.domain.model.HotelSearchCount;
import com.leonardo.hotel_search_challenge.domain.model.PersistedHotelSearch;
import com.leonardo.hotel_search_challenge.infrastructure.adapter.in.rest.dto.SearchRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public final class TestData {

    private TestData(){}

    public static final UUID SEARCH_ID =
            UUID.fromString("a1b2c3d4-e5f6-7890-abcd-ef1234567890");

    public static final String HOTEL_ID = "1234aBc";
    public static final LocalDate CHECK_IN = LocalDate.of(2023, 12, 29);
    public static final LocalDate CHECK_OUT = LocalDate.of(2023, 12, 31);
    public static final List<Integer> AGES = List.of(30, 29, 1, 3);
    public static final Instant OCCURRED_AT = Instant.parse("2023-12-15T10:30:00Z");
    public static final long COUNT = 100L;
    public static final HotelSearch HOTEL_SEARCH = new HotelSearch(HOTEL_ID, CHECK_IN, CHECK_OUT, AGES);
    public static final PersistedHotelSearch PERSISTED_HOTEL_SEARCH = new PersistedHotelSearch(SEARCH_ID, HOTEL_SEARCH, OCCURRED_AT);
    public static final HotelSearchedEvent HOTEL_SEARCHED_EVENT = new HotelSearchedEvent(SEARCH_ID, HOTEL_SEARCH, OCCURRED_AT);
    public static final HotelSearchCount HOTEL_SEARCH_COUNT = new HotelSearchCount(SEARCH_ID, HOTEL_SEARCH, COUNT);
    public static final SearchRequest SEARCH_REQUEST = new SearchRequest(HOTEL_ID, CHECK_IN, CHECK_OUT, AGES);

}
