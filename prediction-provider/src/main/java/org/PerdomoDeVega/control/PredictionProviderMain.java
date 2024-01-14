package org.PerdomoDeVega.control;

import org.PerdomoDeVega.exceptions.StoreException;
import org.PerdomoDeVega.model.Weather;
import org.PerdomoDeVega.model.WeatherPredictionEvent;
import org.apache.activemq.ActiveMQConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PredictionProviderMain {
    public static void main(String[] args) {
        WeatherController weatherController = new WeatherController(args[0], new OpenWeatherMapProvider(), new WeatherEventStore());
        List<String> f = new ArrayList<>();
        f.add("Weather.Prediction");

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
        try {
            weatherController.getWeatherStore().prepareForStore(ActiveMQConnection.DEFAULT_BROKER_URL, f);
        } catch (StoreException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < 8; i++) {
            List<Weather> weathers = weatherController.getWeatherProvider().getWeatherData(weatherController.getLocations().get(i), args[1]);
            List<WeatherPredictionEvent> weathersPredictions = getWeatherpredictions(weathers);

            try {
                weatherController.getWeatherStore().StoreData(null, null, weathersPredictions);
            } catch (StoreException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Executing HTTP request function every six hours...");
        System.out.println("Weathers predictions sent correctly.");
    }
        };
        long delay = 0;
        long period = 6 * 60 * 1000;
        timer.scheduleAtFixedRate(task, delay, period);
    }

    private static List<WeatherPredictionEvent> getWeatherpredictions(List<Weather> weathers){
        List<WeatherPredictionEvent> weatherpredictions = new ArrayList<>(weathers.size());

        for (int i = 0; i < weathers.size(); i++) {
            weatherpredictions.add(new WeatherPredictionEvent(weathers.get(i)));
        }

        return weatherpredictions;

    }


}
