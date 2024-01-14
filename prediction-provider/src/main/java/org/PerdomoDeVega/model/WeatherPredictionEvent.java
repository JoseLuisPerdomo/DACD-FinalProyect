package org.PerdomoDeVega.model;

import java.time.Instant;

public class WeatherPredictionEvent {

    private final Instant timestamp = Instant.now();
    private final String ss = "Prediction Provider";
    private final String predictionTs;
    private final String date;
    private final String temperature;
    private final String rain;
    private final String humedity;
    private final String clouds;
    private final String windSpeed;
    private final Location location;

    public WeatherPredictionEvent(Weather weather) {
        this.predictionTs = weather.getTime();
        this.date = weather.getDate();
        this.temperature = weather.getTemperature();
        this.rain = weather.getRain();
        this.humedity = weather.getHumedity();
        this.clouds = weather.getClouds();
        this.windSpeed = weather.getWindSpeed();
        this.location = weather.getLocation();
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getSs() {
        return ss;
    }

    public String getPredictionTs() {
        return predictionTs;
    }

    public String getDate() {
        return date;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getRain() {
        return rain;
    }

    public String getHumedity() {
        return humedity;
    }

    public String getClouds() {
        return clouds;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public Location getLocation() {
        return location;
    }
}
