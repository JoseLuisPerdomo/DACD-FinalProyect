package org.PerdomoDeVega.model;

public class Weather {
    private final String date;
    private final String temperature;
    private final String rain;
    private final String humedity;
    private final String clouds;
    private final String windSpeed;
    private final Location location;

    public Weather(String date, String temperature, String rain, String humedity, String clouds, String windSpeed, Location location) {
        this.date = date;
        this.temperature = temperature;
        this.rain = rain;
        this.humedity = humedity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.location = location;
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

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + date + '\'' +
                ", temperature='" + temperature + '\'' +
                ", rain='" + rain + '\'' +
                ", humedity='" + humedity + '\'' +
                ", clouds='" + clouds + '\'' +
                ", windSpeed='" + windSpeed + '\'' +
                ", location= " +
                 location.toString() +
                '}';
    }
}
