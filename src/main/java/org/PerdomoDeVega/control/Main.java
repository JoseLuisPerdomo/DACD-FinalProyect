package org.PerdomoDeVega.control;

import org.PerdomoDeVega.model.Location;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        String coordinatesPath = "src/main/resources/Locations";
        WeatherController weatherController = new WeatherController(coordinatesPath, new OpenWeatherMapProvider(), new SQLWeatherStore());

        List<Location> locationList = weatherController.getLocations();
        List<String> tableNames = weatherController.getWeatherProvider().addTableNames(locationList);

        weatherController.prepareExecution("CanaryIslandWeather.db", tableNames);
        weatherController.execute("CanaryIslandWeather.db", tableNames);

    }

}
