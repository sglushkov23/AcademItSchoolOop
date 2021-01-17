package ru.academits.java.glushkov.temperature.model;

public interface Scale {
    double convertToKelvin(double temperature);

    double convertFromKelvin(double kelvinTemperature);
}