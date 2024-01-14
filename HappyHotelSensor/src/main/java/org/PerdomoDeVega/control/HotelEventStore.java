package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exception.StoreException;
import org.PerdomoDeVega.model.HotelRates;
import org.PerdomoDeVega.model.HotelRatesEvent;

public interface HotelEventStore {
    void StoreHotelRates(HotelRatesEvent hotelRatesEvent) throws StoreException;
}
