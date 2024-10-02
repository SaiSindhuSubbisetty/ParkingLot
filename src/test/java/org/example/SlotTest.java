package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarNeedsRegistrationNumberException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.SlotIsOccupiedException;
import org.example.Implementations.Car;
import org.example.Implementations.Slot;
import org.example.Implementations.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    public void testSlotIsInitiallyFree() {
        Slot slot = new Slot();
        assertTrue(slot.isFree());
    }

    @Test
    public void testParkCarInFreeSlot() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = slot.park(car);

        assertNotNull(ticket);
        assertFalse(slot.isFree());
        assertTrue(slot.checkingCarInParkingSlot(car));
    }

    @Test
    public void testCannotParkCarInOccupiedSlot() {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        Car anotherCar = new Car("AP-5678", Color.BLUE);

        assertThrows(SlotIsOccupiedException.class, () -> slot.park(anotherCar));
    }

    @Test
    public void testUnparkCarFromOccupiedSlot() throws SlotIsOccupiedException, CarNotFoundException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = slot.park(car);  // Get the ticket from the park method
        Car unparkedCar = slot.unPark(ticket);  // Use the same ticket to unpark

        assertEquals(car, unparkedCar);
        assertTrue(slot.isFree());
    }

    @Test
    public void testCannotUnparkCarFromFreeSlot() {
        Slot slot = new Slot();

        assertThrows(CarNotFoundException.class, () -> slot.unPark(new Ticket()));
    }

    @Test
    public void testHasCarOfSameColor() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertTrue(slot.hasCarOfColor(Color.RED));
    }

    @Test
    public void testHasCarOfDifferentColor() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertFalse(slot.hasCarOfColor(Color.BLUE));
    }

    @Test
    public void testHasCarWithRegistrationNumber() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertTrue(slot.hasCarWithRegistrationNumber("AP-1234"));
    }

    @Test
    public void testHasCarWithRegistrationNumberThrowsException() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1432", Color.YELLOW);

        slot.park(car);

        assertThrows(CarNeedsRegistrationNumberException.class, () -> slot.hasCarWithRegistrationNumber("AP-5678"));
    }

    @Test
    public void testCheckingCarInParkingSlot() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        assertTrue(slot.checkingCarInParkingSlot(car));
        assertFalse(slot.checkingCarInParkingSlot(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testUnparkCarWithInvalidTicket() throws SlotIsOccupiedException {
        Slot slot = new Slot();
        Car car = new Car("AP-1234", Color.RED);

        slot.park(car);

        Ticket invalidTicket = new Ticket();

        assertThrows(InvalidTicketException.class, () -> slot.unPark(invalidTicket));
    }
}

