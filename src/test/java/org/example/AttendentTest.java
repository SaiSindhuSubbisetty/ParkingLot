package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendentTest {

    @Test
    public void testAssignParkingLotToAttendant() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(1);

        assertDoesNotThrow(() -> attendent.assign(parkingLot));
    }

    @Test
    public void testCannotAssignSameParkingLotTwice() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(1);
        attendent.assign(parkingLot);

        Exception exception = assertThrows(ParkingLotAlreadyAssignmentException.class, () -> attendent.assign(parkingLot));
        assertEquals("Parking lot already assigned.", exception.getMessage());
    }

    @Test
    public void testAttendantParksCarAndReturnsTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "Attendant should return a valid parking ticket");
        assertTrue(parkingLot.isCarAlreadyParked(car), "Car should be parked in the parking lot");
    }

    @Test
    public void testAttendantCannotParkSameCarTwice() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car is already parked", exception.getMessage());
    }

    @Test
    public void testUnparkCarWithValidTicket() {
        Attendent attendent = new Attendent();
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
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Ticket invalidTicket = new Ticket(); // Invalid ticket, not tied to any car

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testCannotUnparkSameCarTwice() {
        Attendent attendent = new Attendent();
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
        Attendent attendent = new Attendent();
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
        Attendent attendent = new Attendent();
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

        assertEquals(1, firstLot.countParkedCars(), "First lot should have 2 cars");
        assertEquals(2, secondLot.countParkedCars(), "Second lot should have 1 car");
    }


    @Test
    public void testNormalAttendantParksCarInFirstAvailableLot() {
        Attendent attendent = new Attendent();
        ParkingLot lot1 = new ParkingLot(1);
        ParkingLot lot2 = new ParkingLot(1);
        attendent.assign(lot1);
        attendent.assign(lot2);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "Ticket should not be null");
    }

    @Test
    public void testNormalAttendantThrowsWhenAllLotsFull() {
        Attendent attendent = new Attendent();
        ParkingLot lot = new ParkingLot(1);
        attendent.assign(lot);

        Car car1 = new Car("AP-1234", Color.RED);
        attendent.park(car1); // Fills the lot

        Car car2 = new Car("AP-5678", Color.BLUE);

        assertThrows(ParkingLotIsFullException.class, () -> attendent.park(car2),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testNormalAttendantThrowsWhenCarAlreadyParked() {
        Attendent attendent = new Attendent();
        ParkingLot lot = new ParkingLot(2);
        attendent.assign(lot);

        Car car = new Car("AP-1234", Color.RED);
        attendent.park(car); // Park the car

        assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car),
                "Exception should be thrown when the same car is parked again");
    }

    @Test
    public void testSmartAttendantParksCarInLotWithFewestCars() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(2);
        ParkingLot lot2 = new ParkingLot(2);
        smartAttendent.assign(lot1);
        smartAttendent.assign(lot2);

        Car car1 = new Car("AP-1234", Color.RED);
        smartAttendent.park(car1); // Should park in lot1

        Car car2 = new Car("AP-5678", Color.BLUE);
        Ticket ticket2 = smartAttendent.park(car2); // Should park in lot2 (fewer cars)

        assertNotNull(ticket2, "Ticket should not be null for car2");
        assertTrue(lot2.isCarAlreadyParked(car2), "Car2 should be parked in lot2");
    }

    @Test
    public void testSmartAttendantThrowsWhenAllLotsFull() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(1);
        ParkingLot lot2 = new ParkingLot(1);
        smartAttendent.assign(lot1);
        smartAttendent.assign(lot2);

        Car car1 = new Car("AP-1234", Color.RED);
        smartAttendent.park(car1); // Fills lot1

        Car car2 = new Car("AP-5678", Color.BLUE);
        smartAttendent.park(car2); // Fills lot2

        Car car3 = new Car("AP-9012", Color.GREEN);
        assertThrows(ParkingLotIsFullException.class, () -> smartAttendent.park(car3),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testSmartAttendantThrowsWhenNoParkingLotAssigned() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        Car car = new Car("AP-1234", Color.RED);

        assertThrows(NoParkingLotAssignedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when no parking lot is assigned");
    }

    @Test
    public void testSmartAttendantCannotParkSameCarTwice() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot = new ParkingLot(2);
        smartAttendent.assign(lot);

        Car car = new Car("AP-1234", Color.RED);
        smartAttendent.park(car); // Park the car

        assertThrows(CarAlreadyParkedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when trying to park the same car again");
    }

    @Test
    public void testSmartAttendantUnparksCarWithValidTicket() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(2);
        ParkingLot lot2 = new ParkingLot(2);
        smartAttendent.assign(lot1);
        smartAttendent.assign(lot2);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = smartAttendent.park(car);

        Car unparkedCar = smartAttendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
        assertFalse(lot1.isCarAlreadyParked(car), "The car should no longer be parked in the lot");
    }

    @Test
    public void testSmartAttendantUnparksCarWithInvalidTicket() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(2);
        smartAttendent.assign(lot1);

        Ticket invalidTicket = new Ticket();

        assertThrows(CarNotFoundException.class, () -> smartAttendent.unpark(invalidTicket),
                "Exception should be thrown for an invalid ticket");
    }

    @Test
    public void testSmartAttendantAssignsMultipleLots() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(2);
        ParkingLot lot2 = new ParkingLot(3);

        assertDoesNotThrow(() -> {
            smartAttendent.assign(lot1);
            smartAttendent.assign(lot2);
        }, "No exception should be thrown when assigning multiple parking lots");
    }

    @Test
    public void testSmartAttendantParksCarsInLotWithMoreCapacity() {
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        ParkingLot lot1 = new ParkingLot(1);
        ParkingLot lot2 = new ParkingLot(3);
        smartAttendent.assign(lot1);
        smartAttendent.assign(lot2);

        Car car1 = new Car("AP-1234", Color.RED);
        Car car2 = new Car("AP-5678", Color.BLUE);

        smartAttendent.park(car1); // Should park in lot1
        smartAttendent.park(car2); // Should park in lot2 (more capacity)

        assertTrue(lot1.isCarAlreadyParked(car1), "Car1 should be parked in lot1");
        assertTrue(lot2.isCarAlreadyParked(car2), "Car2 should be parked in lot2");
    }

}
