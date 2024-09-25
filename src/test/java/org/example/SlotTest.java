package org.example;

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
        String ticket = slot.park(car);
        assertNotNull(ticket, "Ticket should not be null");
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
        Car unparkedCar = slot.unPark("Ticket-1234");
        assertEquals(car, unparkedCar);
        assertTrue(slot.isFree());
    }

    @Test
    void testCannotUnparkCarFromFreeSlot() {
        Slot slot = new Slot(1);
        assertThrows(CarNotFoundException.class, () -> slot.unPark("Ticket-1234"));
    }

    @Test
    void testGetSlotNumber() {
        Slot slot = new Slot(1);
        assertEquals(1, slot.getSlotNumber());
    }

    @Test
    void testGetCars() throws SlotIsOccupiedException {
        Slot slot = new Slot(1);
        Car car = new Car("AP-1234", Color.RED);
        slot.park(car);
        assertEquals(1, slot.getCars().size());
        assertEquals(car, slot.getCars().get(0));
    }
}