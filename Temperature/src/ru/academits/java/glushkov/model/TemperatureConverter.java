package ru.academits.java.glushkov.model;

import java.util.HashMap;

public class TemperatureConverter {
    private static final double LOWEST_KELVIN_TEMPERATURE = 0;
    private static final double LOWEST_CELSIUS_TEMPERATURE = -273.15;
    private static final double LOWEST_FAHRENHEIT_TEMPERATURE = -459.67;
    private final HashMap<String, Double> scaleToMinTemperatureMap;

    public TemperatureConverter() {
        scaleToMinTemperatureMap = new HashMap<>();
        scaleToMinTemperatureMap.put("Celsius", LOWEST_CELSIUS_TEMPERATURE);
        scaleToMinTemperatureMap.put("Fahrenheit", LOWEST_FAHRENHEIT_TEMPERATURE);
        scaleToMinTemperatureMap.put("Kelvin", LOWEST_KELVIN_TEMPERATURE);
    }

    public double convertCelsiusToKelvin(double celsiusTemperature) {
        checkTemperature(celsiusTemperature, "Celsius");

        return celsiusTemperature + 273.15;
    }

    public double convertCelsiusToFahrenheit(double celsiusTemperature) {
        checkTemperature(celsiusTemperature, "Celsius");

        return 1.8 * celsiusTemperature + 32;
    }

    public double convertKelvinToCelsius(double kelvinTemperature) {
        checkTemperature(kelvinTemperature, "Kelvin");

        return kelvinTemperature - 273.15;
    }

    public double convertKelvinToFahrenheit(double kelvinTemperature) {
        checkTemperature(kelvinTemperature, "Kelvin");

        return 1.8 * kelvinTemperature - 459.67;
    }

    public double convertFahrenheitToCelsius(double fahrenheitTemperature) {
        checkTemperature(fahrenheitTemperature, "Fahrenheit");

        return (fahrenheitTemperature - 32) / 1.8;
    }

    public double convertFahrenheitToKelvin(double fahrenheitTemperature) {
        checkTemperature(fahrenheitTemperature, "Fahrenheit");

        return (fahrenheitTemperature + 459.67) / 1.8;
    }

    private void checkTemperature(double temperature, String scale) {
        double minTemperature = scaleToMinTemperatureMap.get(scale);

        if (temperature < minTemperature) {
            throw new IllegalArgumentException(String.format(
                    "The temperature on the %s scale must be not less than %.2f degrees", scale, minTemperature));
        }
    }
}