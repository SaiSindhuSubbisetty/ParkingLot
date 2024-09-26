package org.example;

import org.example.Enums.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    public void testCarCreation() {
        Car car = new Car("AP-1234", Color.RED);

        assertNotNull(car, "Car object should not be null");
        assertTrue(car.hasRegistrationNumber("AP-1234"), "Registration number should match");
        assertTrue(car.isColor(Color.RED), "Color should match");
    }

    @Test
    public void testCarEquality() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-1234", Color.RED);
        Car thirdCar = new Car("AP-5678", Color.BLUE);

        assertEquals(firstCar, secondCar);
        assertNotEquals(firstCar, thirdCar);
    }

    @Test
    public void testCarHashCode() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-1234", Color.RED);

        assertEquals(firstCar.hashCode(), secondCar.hashCode());
    }

    @Test
    public void testCarInequalityWithDifferentRegistrationNumber() {
        Car car = new Car("AP-1234", Color.RED);
        Car anotherCar = new Car("AP-5678", Color.RED);

        assertNotEquals(car, anotherCar);
    }

    @Test
    public void testCarInequalityWithDifferentColor() {
        Car car = new Car("AP-1234", Color.RED);
        Car anotherCar = new Car("AP-1234", Color.BLUE);

        assertNotEquals(car, anotherCar);
    }

    @Test
    public void testCarInequalityWithDifferentObject() {
        Car car = new Car("AP-1234", Color.RED);
        String notACar = "Not a car";

        assertNotEquals(car, notACar);
    }

    @Test
    public void testCarInequalityWithNull() {
        Car car = new Car("AP-1234", Color.RED);

        assertNotEquals(car, null);
    }

    @Test
    public void testCarWithColorYellow() {
        Car car = new Car("AP-1432", Color.YELLOW);

        assertTrue(car.isColor(Color.YELLOW));
    }

    @Test
    public void testCarWithInorrectColorYellow() {
        Car car = new Car("AP-1432", Color.YELLOW);

        assertFalse(car.isColor(Color.BLUE));
    }
}