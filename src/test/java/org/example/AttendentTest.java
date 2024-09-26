package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttendentTest {

    @Test
    void testAssignParkingLotToAttendent() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(1);

        assertDoesNotThrow(() -> attendent.assign(parkingLot));
    }

    @Test
    void testAssignTwoParkingLotsToAttendent() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        Attendent attendent = new Attendent();
        attendent.assign(firstParkingLot);

        assertDoesNotThrow(() -> attendent.assign(secondParkingLot));
    }

    @Test
    void testCannotAssignSameParkingLotTwice() {
        ParkingLot parkingLot = new ParkingLot(1);
        Attendent attendent = new Attendent();
        attendent.assign(parkingLot);

        assertThrows(ParkingLotAlreadyAssignmentException.class, () -> attendent.assign(parkingLot));
    }

    @Test
    void testAttendantParksCarAndReturnsTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "Attendant should return a valid parking ticket");
        assertTrue(parkingLot.isCarParked(car), "Car should be parked in the parking lot");
    }

    @Test
    void testAttendantUnparksCarWithTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);
        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "The car returned should be the same as the one that was parked");
        assertFalse(parkingLot.isCarParked(car), "The car should no longer be parked in the parking lot");
    }

    @Test
    void testAttendantUnparksCarWithInvalidTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Parking lot not found for the given ticket", exception.getMessage());
    }

    @Test
    void testUnparkedCarShouldMatchWithParkedCar() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        attendent.assign(parkingLot1);
        attendent.assign(parkingLot2);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);  // parks in the first lot
        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
    }

    @Test
    void testUnparkCarThatIsNotInAssignedParkingLot() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot1 = new ParkingLot(1);
        ParkingLot parkingLot2 = new ParkingLot(1);
        attendent.assign(parkingLot1);
        attendent.assign(parkingLot2);

        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Parking lot not found for the given ticket", exception.getMessage());
    }

    @Test
    void testAttendantCannotParkSameCarTwice() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car is already parked", exception.getMessage());
    }
}