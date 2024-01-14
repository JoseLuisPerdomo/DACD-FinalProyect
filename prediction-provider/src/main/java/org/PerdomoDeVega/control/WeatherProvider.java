package org.PerdomoDeVega.control;

import org.PerdomoDeVega.model.Location;
import org.PerdomoDeVega.model.Weather;

import java.util.List;

public interface WeatherProvider {

    List<Location> saveLocations(String path);
    List<Weather> getWeatherData(Location location, String apiKey);
    List<String> addTableNames(List<Location> locationList);
}
