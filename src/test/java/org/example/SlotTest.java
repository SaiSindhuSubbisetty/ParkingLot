package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.SlotIsOccupiedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    void testSlotIsInitiallyFree() {
        Slot slot = new Slot();
        assertTrue(slot.isFree());
    }

    @Test
    void testParkCarInFreeSlot() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertEquals(false, slot.isFree());
        assertEquals(car, slot.car);
    }

    @Test
    void testCannotParkCarInOccupiedSlot() {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        Car anotherCar = new Car("AP-5678", Color.BLUE);

        assertThrows(SlotIsOccupiedException.class, () -> slot.park(anotherCar));
    }

    @Test
    void testUnparkCarFromOccupiedSlot() throws SlotIsOccupiedException, CarNotFoundException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = slot.park(car);  // Get the ticket from the park method
        Car unparkedCar = slot.unPark(ticket);  // Use the same ticket to unpark

        assertEquals(car, unparkedCar);
        assertTrue(slot.isFree());
    }

    @Test
    void testCannotUnparkCarFromFreeSlot() {
        Slot slot = new Slot();

        assertThrows(CarNotFoundException.class, () -> slot.unPark(new Ticket()));
    }
}