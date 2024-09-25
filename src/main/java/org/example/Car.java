package org.example;

import java.util.Objects;

public class Car {
    final String registrationNumber;
    final Color color;

    public Car(String registrationNumber, Color color) {
        this.registrationNumber = registrationNumber;
        this.color = color;
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
