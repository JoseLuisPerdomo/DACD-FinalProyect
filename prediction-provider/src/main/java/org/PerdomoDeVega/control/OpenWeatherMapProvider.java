package org.PerdomoDeVega.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.PerdomoDeVega.model.Location;
import org.PerdomoDeVega.model.Weather;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

public class OpenWeatherMapProvider implements WeatherProvider{

    public List<Location> locations;

    public OpenWeatherMapProvider() {
        this.locations = new ArrayList<>();
    }


    public List<Location> saveLocations(String path) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {

                String[] partes = line.split(";");

                if (partes.length >= 3) {
                    String name = partes[0].trim();
                    String latitude = partes[1].trim();
                    String longitude = partes[2].trim();

                    Location location = new Location(name, latitude, longitude);
                    locations.add(location);
                } else {
                    System.err.println("Error while reading locations: " + line);
                    return null;
                }

            }
            return locations;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> addTableNames(List<Location> locationList) {

        List<String> tableNames = new ArrayList<>(locationList.size());

        for (Location location : locationList) {
            tableNames.add(location.getName());
        }

        return tableNames;


    }

    @Override
    public List<Weather> getWeatherData(Location location, String apiKey) {
        String apiRequest = String.format("https://api.openweathermap.org/data/2.5/forecast?lat=%s&lon=%s&units=metric&appid=" + apiKey, location.getLatitude(), location.getLongitude());
        try {
            Connection.Response response = Jsoup.connect(apiRequest)
                    .ignoreContentType(true)
                    .execute();

            String jsonResponse = response.body();
            JsonObject weatherJson = JsonParser.parseString(jsonResponse).getAsJsonObject();
            List<JsonObject> ListJsons = getNext5DaysPrediction(weatherJson);
            return getPredictionWeathers(ListJsons, location);
        } catch (IOException e) {
            e.getCause();
            return null;
        }
    }

    public List<Weather> getPredictionWeathers(List<JsonObject> weatherJson, Location location){
        List<Weather> weatherList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            List<String> weatherValues = getRelevantVariables(weatherJson.get(i));
            weatherList.add(new Weather(weatherValues.get(0), weatherValues.get(1), weatherValues.get(2), weatherValues.get(3), weatherValues.get(4), weatherValues.get(5), weatherValues.get(6), location));
        }
        return weatherList;
    }

    public List<JsonObject> getNext5DaysPrediction(JsonObject weatherJson) {
        JsonObject jsonObject = JsonParser.parseString(String.valueOf(weatherJson)).getAsJsonObject();

        JsonArray listArray = jsonObject.getAsJsonArray("list");

        return StreamSupport.stream(listArray.spliterator(), false)
                .map(JsonObject.class::cast)
                .filter(item -> item.getAsJsonPrimitive("dt_txt").getAsString().endsWith("12:00:00"))
                .toList();
    }

    public List<String> getRelevantVariables (JsonObject weatherJson){

        List<String> WeatherValues = new ArrayList<>();
        if (weatherJson != null) {
            String day = weatherJson.get("dt_txt").getAsString().substring(0, 10);
            String time = weatherJson.get("dt_txt").getAsString().substring(9);
            String temperature = weatherJson.getAsJsonObject("main").get("temp").getAsString();
            String precipitation = weatherJson.get("pop").getAsString();
            String humidity = weatherJson.getAsJsonObject("main").get("humidity").getAsString();
            String clouds = weatherJson.getAsJsonObject("clouds").get("all").getAsString();
            String windSpeed = weatherJson.getAsJsonObject("wind").get("speed").getAsString();
            WeatherValues.add(day);
            WeatherValues.add(time);
            WeatherValues.add(String.valueOf(temperature).replace(',', '.'));
            WeatherValues.add(String.valueOf(precipitation).replace(',', '.'));
            WeatherValues.add(String.valueOf(humidity).replace(',', '.'));
            WeatherValues.add(String.valueOf(clouds).replace(',', '.'));
            WeatherValues.add(String.valueOf(windSpeed).replace(',', '.'));
        } else {
            System.out.println("Error while getting the weather prediction.");
        }
        return WeatherValues;
    }
}
