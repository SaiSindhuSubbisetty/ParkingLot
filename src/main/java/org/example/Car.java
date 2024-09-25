package org.example;

import java.util.Objects;

public class Car {
    private final String registrationNumber;
    private final Color color;

    public Car(String registrationNumber, Color color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(registrationNumber, car.registrationNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

}
