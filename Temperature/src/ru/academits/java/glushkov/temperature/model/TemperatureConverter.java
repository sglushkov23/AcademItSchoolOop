package ru.academits.java.glushkov.temperature.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TemperatureConverter implements Converter {
    private final Scale[] scales;

    public TemperatureConverter(Scale[] scales) {
        if (scales == null) {
            throw new NullPointerException("Constructor's scales argument must be not null");
        }

        if (scales.length < 2) {
            throw new IllegalArgumentException(
                    "Length of scales array must be bigger than 1: scales argument length = " + scales.length
            );
        }

        Set<Scale> uniqueScales = new HashSet<>();

        for (Scale scale : scales) {
            if (!uniqueScales.add(scale)) {
                throw new IllegalArgumentException(
                        "There is duplicate in constructor's argument scales: \"" + scale + "\" scale found more than once"
                );
            }
        }

        this.scales = Arrays.copyOf(scales, scales.length);
    }

    @Override
    public Scale[] getScales() {
        return scales;
    }

    @Override
    public double convertTemperature(double temperature, Scale from, Scale to) {
        return to.convertFromKelvin(from.convertToKelvin(temperature));
    }
}