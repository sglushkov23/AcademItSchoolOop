package ru.academits.java.glushkov.temperature_main;

import ru.academits.java.glushkov.temperature.controller.TemperatureController;
import ru.academits.java.glushkov.temperature.model.*;
import ru.academits.java.glushkov.temperature.view.DesktopView;
import ru.academits.java.glushkov.temperature.view.View;

public class Main {
    public static void main(String[] args) {
        Scale[] scales = {
                new FahrenheitScale(),
                new CelsiusScale(),
                new KelvinScale(),
                new RankineScale()
        };

        Converter temperatureConverter = new TemperatureConverter(scales);
        TemperatureController controller = new TemperatureController(temperatureConverter);
        View view = new DesktopView(controller);

        controller.setView(view);
        view.start();
    }
}
