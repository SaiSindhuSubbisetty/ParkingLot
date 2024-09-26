package org.example;

import org.example.Enums.Color;

import java.util.Objects;


public class Car {
    private final String registrationNumber;
    private final Color color;

    public Car(String registrationNumber, Color color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
    }

    public boolean isColor(Color color) {
        return this.color == color;
    }

    public boolean hasRegistrationNumber(String registrationNumber) {
        return this.registrationNumber.equals(registrationNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(registrationNumber, car.registrationNumber) && color == car.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber);
    }

}