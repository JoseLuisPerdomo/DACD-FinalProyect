package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Location;
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
        List<String> topic = new ArrayList<>();
        topic.add("Weather.prediction.test");
        Weather weather = new Weather("2023", "234", "323", "65", "22", "1", new Location("Aqui", "o", "que"));
        List<Weather> weatherList = new ArrayList<>();
        weatherList.add(weather);
        try {
            weatherEventStore.prepareForStore(ActiveMQConnection.DEFAULT_BROKER_URL, topic);
            weatherEventStore.StoreData("Si", "Hola", weatherList);
        } catch (StoreException e) {
            System.out.println(e.getMessage());
        }

    }

}
