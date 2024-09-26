package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.SlotIsOccupiedException;
import org.example.Implementations.Car;
import org.example.Implementations.Slot;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    void testSlotIsInitiallyFree() {
        Slot slot = new Slot(1);
        assertTrue(slot.isFree());
    }

    @Test
    void testParkCarInFreeSlot() throws SlotIsOccupiedException {
        Slot slot = new Slot(1);
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertEquals(false, slot.isFree());
        assertEquals(car, slot.car);
    }

    @Test
    void testCannotParkCarInOccupiedSlot(){
        Slot slot = new Slot(1);
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        Car anotherCar = new Car("AP-5678", Color.BLUE);

        assertThrows(SlotIsOccupiedException.class, () -> slot.park(anotherCar));
    }

    @Test
    void testUnparkCarFromOccupiedSlot() throws SlotIsOccupiedException, CarNotFoundException {
        Slot slot = new Slot(1);
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);
        Car unparkedCar = slot.unPark();

        assertEquals(car, unparkedCar);
        assertEquals(true, slot.isFree());
    }

    @Test
    void testCannotUnparkCarFromFreeSlot() {
        Slot slot = new Slot(1);

        assertThrows(CarNotFoundException.class, () -> slot.unPark());
    }

    @Test
    void testGetSlotNumber() {
        Slot slot = new Slot(7);

        assertEquals(7, slot.slotNumber);
    }
}