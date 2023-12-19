package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.apache.activemq.ActiveMQConnection;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        //TODO Resolver problema de la ApiKey y de las rutas absolutas y relativas.

        //String coordinatesPath = "prediction-provider/src/main/resources/Locations";
        //WeatherController weatherController = new WeatherController(coordinatesPath, new OpenWeatherMapProvider(), new SQLWeatherStore());

        //List<Location> locationList = weatherController.getLocations();
        //List<String> tableNames = weatherController.getWeatherProvider().addTableNames(locationList);

        //weatherController.prepareExecution("CanaryIslandWeather.db", tableNames);
        //weatherController.execute("CanaryIslandWeather.db", tableNames);

        WeatherEventStore weatherEventStore = new WeatherEventStore();
        OpenWeatherMapProvider openWeatherMapProvider = new OpenWeatherMapProvider();
        List<String> topic = new ArrayList<>();
        topic.add("Weather.prediction.test");
        List<Weather> weatherList;
        for (int i = 0; i < 8; i++) {
            weatherList = openWeatherMapProvider.getWeatherData(openWeatherMapProvider.saveLocations("prediction-provider/src/main/resources/Locations").get(i));
            try {
                weatherEventStore.prepareForStore(ActiveMQConnection.DEFAULT_BROKER_URL, topic);
                weatherEventStore.StoreData("", "", weatherList);
            } catch (StoreException e) {
                System.out.println(e.getMessage());
            }
        }

    }

}
