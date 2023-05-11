package com.a1.disasterresponse.model;

public class WeatherData {
    private double temperature;
    private double windSpeed;
    private double windDirection;

    public WeatherData(double temperature, double windSpeed, double windDirection) {
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }
}
