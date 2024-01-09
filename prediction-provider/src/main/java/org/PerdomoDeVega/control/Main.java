package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.apache.activemq.ActiveMQConnection;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(args[0], new OpenWeatherMapProvider(), new WeatherEventStore());
        List<String> f = new ArrayList<>();
        f.add("Weather.Prediction");
        try {
            weatherController.getWeatherStore().prepareForStore(ActiveMQConnection.DEFAULT_BROKER_URL, f);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 8; i++) {
            List<Weather> weathers = weatherController.getWeatherProvider().getWeatherData(weatherController.getLocations().get(i));

            try {
                weatherController.getWeatherStore().StoreData(null, null, weathers);
            } catch (StoreException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
