package org.PerdomoDeVega.model;

public class Weather {
    private final String day;
    private final String time;
    private final String temperature;
    private final String rain;
    private final String humedity;
    private final String clouds;
    private final String windSpeed;
    private final Location location;

    public Weather(String date, String time, String temperature, String rain, String humedity, String clouds, String windSpeed, Location location) {
        this.time = time;
        this.day = date;
        this.temperature = temperature;
        this.rain = rain;
        this.humedity = humedity;
        this.clouds = clouds;
        this.windSpeed = windSpeed;
        this.location = location;
    }

    public String getDate() {
        return day;
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
    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "date='" + day + '\'' +
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
