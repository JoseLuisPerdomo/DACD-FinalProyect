package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.apache.activemq.ActiveMQConnection;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController("C:\\Users\\Usuario\\IdeaProjects\\Practica Prediction\\prediction-provider\\src\\main\\resources\\Locations", new OpenWeatherMapProvider(), new WeatherEventStore());
        List<String> f = new ArrayList<>();
        f.add("Weather.prediction.test");
        try {
            weatherController.getWeatherStore().prepareForStore(ActiveMQConnection.DEFAULT_BROKER_URL, f);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
        List<Weather> weathers = weatherController.getWeatherProvider().getWeatherData(weatherController.getLocations().get(0));

        try {
            weatherController.getWeatherStore().StoreData(null, null, weathers);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
    }
}
