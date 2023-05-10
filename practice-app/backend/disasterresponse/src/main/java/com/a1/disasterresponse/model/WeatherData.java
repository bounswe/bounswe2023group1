package com.a1.disasterresponse.model;

public class WeatherData {

    private final double temperature;
    private final String conditionText;
    private final String conditionIcon;

    public WeatherData(double temperature, String conditionText, String conditionIcon) {
        this.temperature = temperature;
        this.conditionText = conditionText;
        this.conditionIcon = conditionIcon;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getConditionText() {
        return conditionText;
    }

    public String getConditionIcon() {
        return conditionIcon;
    }
}

