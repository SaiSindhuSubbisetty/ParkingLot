package org.example;

import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.SlotIsOccupiedException;
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
        assertFalse(slot.isFree());
    }

    @Test
    void testCannotParkCarInOccupiedSlot() throws SlotIsOccupiedException {
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
        assertTrue(slot.isFree());
    }

    @Test
    void testCannotUnparkCarFromFreeSlot() {
        Slot slot = new Slot(1);
        assertThrows(CarNotFoundException.class, () -> slot.unPark());
    }

    @Test
    void testGetSlotNumber() {
        Slot slot = new Slot(1);
        assertEquals(1, slot.getSlotNumber());
    }
}