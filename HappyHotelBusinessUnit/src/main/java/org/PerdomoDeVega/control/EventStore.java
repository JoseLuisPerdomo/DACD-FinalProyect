package org.PerdomoDeVega.control;

public interface EventStore {

    void StoreWeather(String Weather);
    void StoreHotelRates(String hotelRates);

}
