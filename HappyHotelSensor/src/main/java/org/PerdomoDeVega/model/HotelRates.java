package org.PerdomoDeVega.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HotelRates {
    private final Map<String,Integer> rates = new HashMap<>();
    private final List<String> checkInCheckOut;
    private final String currency;
    private final String adults;
    private final String childrens;
    private final String rooms;
    private final String hotelName;
    private final String island;


    public HotelRates(String hotelName, List<String> hotelOptions, List<Integer> hotelPrices, List<String> checkInCheckOut, String currency, String adults, String childrens, String rooms, String island) {
        this.hotelName = hotelName;
        for (int i = 0; i < hotelPrices.size(); i++) {
            rates.put(hotelOptions.get(i), hotelPrices.get(i));
        }
        this.checkInCheckOut = checkInCheckOut;
        this.currency = currency;
        this.adults = adults;
        this.childrens = childrens;
        this.rooms = rooms;
        this.island = island;
    }

    public Map<String, Integer> getRates() {
        return rates;
    }

    public List<String> getCheckInCheckOut() {
        return checkInCheckOut;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAdults() {
        return adults;
    }

    public String getChildrens() {
        return childrens;
    }

    public String getRooms() {
        return rooms;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getIsland() {
        return island;
    }
}
