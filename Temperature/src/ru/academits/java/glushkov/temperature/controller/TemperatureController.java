package ru.academits.java.glushkov.temperature.controller;

import ru.academits.java.glushkov.temperature.model.Scale;
import ru.academits.java.glushkov.temperature.model.Converter;
import ru.academits.java.glushkov.temperature.view.View;

public class TemperatureController implements Controller {
    private View view;
    private final Converter temperatureConverter;

    public TemperatureController(Converter temperatureConverter) {
        this.temperatureConverter = temperatureConverter;
    }

    @Override
    public Converter getConverter() {
        return temperatureConverter;
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void convert(double temperature, Scale from, Scale to) {
        view.setOutputTemperature(temperatureConverter.convertTemperature(temperature, from, to));
    }
}