package ru.academits.java.glushkov.controller;

import ru.academits.java.glushkov.model.TemperatureConverter;
import ru.academits.java.glushkov.view.View;

public class Controller {
    private View view;
    private final TemperatureConverter temperatureConverter;

    public Controller(TemperatureConverter temperatureConverter) {
        this.temperatureConverter = temperatureConverter;
    }

    public void setView(View view) {
        this.view = view;
    }

    public void convertTemperature(double temperature, String from, String to) {
        double result;

        if (from.equals("Celsius") && to.equals("Kelvin")) {
            result = temperatureConverter.convertCelsiusToKelvin(temperature);
        } else if (from.equals("Celsius") && to.equals("Fahrenheit")) {
            result = temperatureConverter.convertCelsiusToFahrenheit(temperature);
        } else if (from.equals("Kelvin") && to.equals("Celsius")) {
            result = temperatureConverter.convertKelvinToCelsius(temperature);
        } else if (from.equals("Kelvin") && to.equals("Fahrenheit")) {
            result = temperatureConverter.convertKelvinToFahrenheit(temperature);
        } else if (from.equals("Fahrenheit") && to.equals("Celsius")) {
            result = temperatureConverter.convertFahrenheitToCelsius(temperature);
        } else {
            result = temperatureConverter.convertFahrenheitToKelvin(temperature);
        }

        view.setTemperature(result);
    }
}