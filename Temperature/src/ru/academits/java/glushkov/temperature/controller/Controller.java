package ru.academits.java.glushkov.temperature.controller;

import ru.academits.java.glushkov.temperature.model.Converter;
import ru.academits.java.glushkov.temperature.model.Scale;
import ru.academits.java.glushkov.temperature.view.View;

public interface Controller {
    void setView(View view);

    void convert(double value, Scale from, Scale to);

    Converter getConverter();
}