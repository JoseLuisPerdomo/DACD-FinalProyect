package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;

import java.util.List;

public interface WeatherStore {
    void StoreData(String dbPath, String tableName, List<Weather> weatherData) throws StoreException;
    void prepareForStore(String dbPath, List<String> tableNames) throws StoreException;
}
