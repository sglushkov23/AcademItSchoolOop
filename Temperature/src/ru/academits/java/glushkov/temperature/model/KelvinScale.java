package ru.academits.java.glushkov.temperature.model;

public class KelvinScale implements Scale {
    private static final double LOWEST_TEMPERATURE = 0;
    private static final String scaleName = "Kelvin";

    @Override
    public double convertToKelvin(double kelvinTemperature) {
        checkTemperature(kelvinTemperature);

        return kelvinTemperature;
    }

    @Override
    public double convertFromKelvin(double kelvinTemperature) {
        return kelvinTemperature;
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