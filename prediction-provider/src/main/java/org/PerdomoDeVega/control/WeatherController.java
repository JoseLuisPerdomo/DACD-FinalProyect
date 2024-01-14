package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Location;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {

    private List<Location> locations;
    private final WeatherProvider weatherProvider;
    private final WeatherStore weatherStore;

    public WeatherController (String coordinatesPath, WeatherProvider weatherProvider, WeatherStore weatherStore){
        this.locations = weatherProvider.saveLocations(coordinatesPath);
        this.weatherProvider = weatherProvider;
        this.weatherStore = weatherStore;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public WeatherProvider getWeatherProvider() {
        return weatherProvider;
    }

    public void prepareExecution(String dbPath, List<String> tableNames){
        try {
            weatherStore.prepareForStore(dbPath, tableNames);
        } catch (StoreException e) {
            System.out.println(e.getMessage());
        }
    }

    public void execute(String dbPath, List<String> tableNames, String apiKey){

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < tableNames.size(); i++) {
                        weatherStore.StoreData(dbPath, locations.get(i).getName(), null);
                    }
                } catch (StoreException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Executing HTTP request function every six hours...");
            }
        };

        long delay = 0;
        long period = 6 * 60 * 1000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    public WeatherStore getWeatherStore() {
        return weatherStore;
    }
}
