package org.PerdomoDeVega.control;

import org.PerdomoDeVega.model.HotelRates;

public interface HotelProvider {
    HotelRates getHotelRates(String hotelName, String hotelOption, String checkIn, String checkOut, String rooms, String adults, String childrens, String currency, String island);
}
