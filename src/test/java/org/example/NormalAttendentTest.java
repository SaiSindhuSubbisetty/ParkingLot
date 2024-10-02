package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.Car;
import org.example.Implementations.NormalAttendent;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NormalAttendentTest {

    @Test
    public void testAssignParkingLotToAttendant() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(1);

        assertDoesNotThrow(() -> attendent.assign(parkingLot));
    }

    @Test
    public void testCannotAssignSameParkingLotTwice() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(1);
        attendent.assign(parkingLot);

        Exception exception = assertThrows(ParkingLotAlreadyAssignmentException.class, () -> attendent.assign(parkingLot));
        assertEquals("Parking lot already assigned.", exception.getMessage());
    }

    @Test
    public void testAttendantParksCarAndReturnsTicket() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "Attendant should return a valid parking ticket");
        assertTrue(parkingLot.isCarAlreadyParked(car), "Car should be parked in the parking lot");
    }

    @Test
    public void testAttendantCannotParkSameCarTwice() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car already assigned to this parking lot", exception.getMessage());
    }

    @Test
    public void testUnparkCarWithValidTicket() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
        assertFalse(parkingLot.isCarAlreadyParked(car), "Car should no longer be parked in the lot");
    }

    @Test
    public void testUnparkCarWithInvalidTicket() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Ticket invalidTicket = new Ticket(); // Invalid ticket, not tied to any car

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testCannotUnparkSameCarTwice() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(1);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        attendent.unpark(ticket); // First unpark succeeds

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testAttendantThrowsWhenAllLotsFull() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot parkingLot = new ParkingLot(1); // Only 1 spot
        attendent.assign(parkingLot);

        Car car1 = new Car("AP-1234", Color.RED);
        attendent.park(car1); // Fills the lot

        Car car2 = new Car("AP-5678", Color.BLUE);

        Exception exception = assertThrows(ParkingLotIsFullException.class, () -> attendent.park(car2));
        assertEquals("All parking lots are full", exception.getMessage());
    }

    @Test
    public void testAttendantParksCarsSequentially() {
        NormalAttendent attendent = new NormalAttendent();
        ParkingLot firstLot = new ParkingLot(2);
        ParkingLot secondLot = new ParkingLot(2);
        attendent.assign(firstLot);
        attendent.assign(secondLot);

        Car car1 = new Car("AP-1234", Color.RED);
        Car car2 = new Car("AP-5678", Color.BLUE);
        Car car3 = new Car("AP-9101", Color.GREEN);

        attendent.park(car1);
        attendent.park(car2);
        attendent.park(car3);  // Third car should go to the second lot

        assertEquals(2, firstLot.countParkedCars(), "First lot should have 2 cars");
        assertEquals(1, secondLot.countParkedCars(), "Second lot should have 1 car");
    }


    @Test
    public void testNormalAttendantParksCarInFirstAvailableLot() {
        NormalAttendent normalAttendent = new NormalAttendent();
        ParkingLot lot1 = new ParkingLot(1);
        ParkingLot lot2 = new ParkingLot(1);
        normalAttendent.assign(lot1);
        normalAttendent.assign(lot2);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = normalAttendent.park(car);

        assertNotNull(ticket, "Ticket should not be null");
        assertTrue(lot1.isCarAlreadyParked(car), "Car should be parked in lot1");
    }

    @Test
    public void testNormalAttendantThrowsWhenAllLotsFull() {
        NormalAttendent normalAttendent = new NormalAttendent();
        ParkingLot lot = new ParkingLot(1);
        normalAttendent.assign(lot);

        Car car1 = new Car("AP-1234", Color.RED);
        normalAttendent.park(car1); // Fills the lot

        Car car2 = new Car("AP-5678", Color.BLUE);

        assertThrows(ParkingLotIsFullException.class, () -> normalAttendent.park(car2),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testNormalAttendantThrowsWhenCarAlreadyParked() {
        NormalAttendent normalAttendent = new NormalAttendent();
        ParkingLot lot = new ParkingLot(2);
        normalAttendent.assign(lot);

        Car car = new Car("AP-1234", Color.RED);
        normalAttendent.park(car); // Park the car

        assertThrows(CarAlreadyParkedException.class, () -> normalAttendent.park(car),
                "Exception should be thrown when the same car is parked again");
    }
}
