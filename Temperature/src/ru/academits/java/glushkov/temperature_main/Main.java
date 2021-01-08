package ru.academits.java.glushkov.temperature_main;

import ru.academits.java.glushkov.controller.Controller;
import ru.academits.java.glushkov.model.TemperatureConverter;
import ru.academits.java.glushkov.view.DesktopView;
import ru.academits.java.glushkov.view.View;

public class Main {
    public static void main(String[] args) {
        TemperatureConverter temperatureConverter = new TemperatureConverter();
        Controller controller = new Controller(temperatureConverter);
        View view = new DesktopView(controller);

        controller.setView(view);
        view.start();
    }
}
