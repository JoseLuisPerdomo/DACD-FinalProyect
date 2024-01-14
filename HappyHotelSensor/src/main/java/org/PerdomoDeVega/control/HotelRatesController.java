package org.PerdomoDeVega.control;

public class HotelRatesController {
    private final HotelProvider hotelProvider;
    private final HotelEventStore hotelEventStore;

    public HotelRatesController(HotelProvider hotelProvider, HotelEventStore hotelEventStore) {
        this.hotelProvider = hotelProvider;
        this.hotelEventStore = hotelEventStore;
    }

    public HotelProvider getHotelProvider() {
        return hotelProvider;
    }

    public HotelEventStore getHotelEventStore() {
        return hotelEventStore;
    }
}
