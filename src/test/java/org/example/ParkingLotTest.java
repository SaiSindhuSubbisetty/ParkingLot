package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.Car;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.Ticket;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    @Test
    void testExceptionNewParkingLotIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
    }

    @Test
    void testExceptionForNegativeParkingSlots() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));
    }

    @Test
    void testCreateParkingLotWith5Slots() {
        ParkingLot parkingLot = new ParkingLot(5);

        assertNotNull(parkingLot);
    }

    @Test
    void testCannotParkSameCarTwice() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-1234", Color.RED);

        parkingLot.park(car);
        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(car));

        assertEquals("Car is already parked", exception.getMessage());
    }

    @Test
    void testParkingLotWithOneSlotIsFullWhenCarParked() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarParked(car));
    }

    @Test
    void testParkingLotWithTwoSlotsIsNotFullWhenOneCarParked() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("AP-1431", Color.BLUE);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarParked(car));
    }

    @Test
    void testParkWith5Slots() {
        Car car = new Car("AP-9876", Color.BLACK);
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarParked(car));
    }

    @Test
    void testParkInFullParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        parkingLot.park(firstCar);
        Exception exception = assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(secondCar));

        assertEquals("Parking lot is full", exception.getMessage());
    }

    @Test
    void testParkInNearestAvailableSlot() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.BLUE);
        ParkingLot parkingLot = new ParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertTrue(parkingLot.isCarParked(firstCar));
        assertTrue(parkingLot.isCarParked(secondCar));
    }

    @Test
    void testParkInNearestAvailableSlotAfterUnparking() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        Car thirdCar = new Car("AP-9999", Color.GREEN);
        Ticket firstCarTicket = parkingLot.park(firstCar);

        parkingLot.park(secondCar);
        parkingLot.unpark(firstCarTicket);
        parkingLot.park(thirdCar);

        assertTrue(parkingLot.isCarParked(thirdCar));
    }

    @Test
    void testUnparkCarThatIsNotParked() {
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket invalidTicket = new Ticket(0, 1);  // Ticket for an empty slot

        Exception exception = assertThrows(CarNotFoundException.class, () -> parkingLot.unpark(invalidTicket));

        assertEquals("Invalid ticket or car not found in the parking lot", exception.getMessage());
    }

    @Test
    void testUnparkCarFromEmptyParkingLot() {
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket invalidTicket = new Ticket(0, 1);  // Empty parking lot

        Exception exception = assertThrows(CarNotFoundException.class, () -> parkingLot.unpark(invalidTicket));

        assertEquals("Invalid ticket or car not found in the parking lot", exception.getMessage());
    }

    @Test
    void testUnpark() {
        Car car = new Car("AP-1234", Color.RED);
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket ticket = parkingLot.park(car);
        Car unparkedCar = parkingLot.unpark(ticket);

        assertEquals(car, unparkedCar);  // Ensure the correct car is returned
    }

    @Test
    void testCountCarsByRedColorIsNotFoundInParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        int count = parkingLot.countCarsByColor(Color.RED);

        assertEquals(0, count);
    }

    @Test
    void testCountCarsByColorNotPresent() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-1234", Color.BLUE);

        parkingLot.park(car);
        assertEquals(0, parkingLot.countCarsByColor(Color.YELLOW));
    }

    @Test
    void testCountCarsByColor() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.RED);
        Car thirdCar = new Car("AP-0001", Color.BLUE);
        ParkingLot parkingLot = new ParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        assertEquals(2, parkingLot.countCarsByColor(Color.RED));
    }

    @Test
    void testIsCarParkedForNonParkedCar() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-1432", Color.YELLOW);

        assertFalse(parkingLot.isCarParked(car));  // Car is not parked
    }

    @Test
    void testIsParkingLotFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-4321", Color.BLUE);

        parkingLot.park(car);
        assertTrue(parkingLot.isFull());  // Parking lot is full after one car is parked
    }

    @Test
    void testIsParkingLotNotFull() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-9876", Color.GREEN);

        parkingLot.park(car);
        assertFalse(parkingLot.isFull());  // Parking lot is not full
    }
}
