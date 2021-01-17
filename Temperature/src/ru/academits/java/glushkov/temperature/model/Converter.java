package ru.academits.java.glushkov.temperature.model;

public interface Converter {
    double convertTemperature(double value, Scale from, Scale to);

    Scale[] getScales();
}