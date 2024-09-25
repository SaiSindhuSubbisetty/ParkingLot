package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CarTest {

    @Test
    void testCarCreation() {
        Car car = new Car("AP-1234", Color.RED);
        assertNotNull(car, "Car object should not be null");
        assertEquals("AP-1234", car.getRegistrationNumber(), "Registration number should match");
        assertEquals(Color.RED, car.getColor());
    }

    @Test
    void testCarEquality() {
        Car firstcar = new Car("AP-1234", Color.RED);
        Car secondcar = new Car("AP-1234", Color.RED);
        Car thirdcar = new Car("AP-5678", Color.BLUE);
        assertEquals(firstcar, secondcar);
        assertNotEquals(firstcar, thirdcar);
    }

    @Test
    void testCarHashCode() {
        Car firstcar = new Car("AP-1234", Color.RED);
        Car secondcar = new Car("AP-1234", Color.RED);
        assertEquals(firstcar.hashCode(), secondcar.hashCode());
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