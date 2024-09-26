package org.example;

import org.example.Enums.Color;
import org.example.Implementations.Car;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarCreation() {
        Car car = new Car("AP-1234", Color.RED);

        assertNotNull(car, "Car object should not be null");
        assertEquals("AP-1234", car.registrationNumber, "Registration number should match");
        assertEquals(Color.RED, car.color);
    }

    @Test
    void testCarEquality() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-1234", Color.RED);
        Car thirdCar = new Car("AP-5678", Color.BLUE);

        assertEquals(firstCar, secondCar);
        assertNotEquals(firstCar, thirdCar);
    }

    @Test
    void testCarHashCode() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-1234", Color.RED);

        assertEquals(firstCar.hashCode(), secondCar.hashCode());
    }

    @Test
    void testCarInequalityWithDifferentObject() {
        Car car = new Car("AP-1234", Color.RED);
        String notACar = "Not a car";

        assertNotEquals(car, notACar);
    }

    @Test
    void testCarInequalityWithNull() {
        Car car = new Car("AP-1234", Color.RED);

        assertNotEquals(car, null);
    }
}
