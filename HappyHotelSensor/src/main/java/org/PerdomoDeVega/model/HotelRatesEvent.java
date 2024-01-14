package org.PerdomoDeVega.model;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class HotelRatesEvent {

    private final Instant timestamp = Instant.now();
    private final String ss = "Happy Hotel Sensor";
    private final Map<String,Integer> rates;
    private final List<String> checkInCheckOut;
    private final String currency;
    private final String adults;
    private final String childrens;
    private final String rooms;
    private final String hotelName;
    private final String island;


    public HotelRatesEvent(HotelRates hotelRates) {
        this.rates = hotelRates.getRates();
        this.checkInCheckOut = hotelRates.getCheckInCheckOut();
        this.currency = hotelRates.getCurrency();
        this.adults = hotelRates.getAdults();
        this.childrens = hotelRates.getChildrens();
        this.rooms = hotelRates.getRooms();
        this.hotelName = hotelRates.getHotelName();
        this.island = hotelRates.getIsland();
    }
}
