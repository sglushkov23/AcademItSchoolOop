package ru.academits.java.glushkov.temperature.model;

public class FahrenheitScale implements Scale {
    private static final double LOWEST_TEMPERATURE = -459.67;
    private final String scaleName = "Fahrenheit";

    @Override
    public double convertToKelvin(double fahrenheitTemperature) {
        checkTemperature(fahrenheitTemperature);

        return (fahrenheitTemperature - LOWEST_TEMPERATURE) / 1.8;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperature) {
        return 1.8 * kelvinTemperature + LOWEST_TEMPERATURE;
    }

    @Override
    public String toString() {
        return scaleName;
    }

    private void checkTemperature(double temperature) {
        if (temperature < LOWEST_TEMPERATURE) {
            throw new IllegalArgumentException(String.format(
                    "The temperature on the %s scale must be not less than %.2f degrees", scaleName, LOWEST_TEMPERATURE)
            );
        }
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Double.hashCode(LOWEST_TEMPERATURE);
        hash = prime * hash + scaleName.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        return o != null && o.getClass() == getClass();
    }
}