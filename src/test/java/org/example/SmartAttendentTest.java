package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.Car;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.SmartAttendent;
import org.example.Implementations.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SmartAttendentTest {

    @Test
    public void testSmartAttendantParksCarInLotWithFewestCars() {
        SmartAttendent smartAttendent = new SmartAttendent();
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
        SmartAttendent smartAttendent = new SmartAttendent();
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
        SmartAttendent smartAttendent = new SmartAttendent();
        Car car = new Car("AP-1234", Color.RED);

        assertThrows(NoParkingLotAssignedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when no parking lot is assigned");
    }

    @Test
    public void testSmartAttendantCannotParkSameCarTwice() {
        SmartAttendent smartAttendent = new SmartAttendent();
        ParkingLot lot = new ParkingLot(2);
        smartAttendent.assign(lot);

        Car car = new Car("AP-1234", Color.RED);
        smartAttendent.park(car); // Park the car

        assertThrows(CarAlreadyParkedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when trying to park the same car again");
    }

    @Test
    public void testSmartAttendantUnparksCarWithValidTicket() {
        SmartAttendent smartAttendent = new SmartAttendent();
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
        SmartAttendent smartAttendent = new SmartAttendent();
        ParkingLot lot1 = new ParkingLot(2);
        smartAttendent.assign(lot1);

        Ticket invalidTicket = new Ticket();

        assertThrows(CarNotFoundException.class, () -> smartAttendent.unpark(invalidTicket),
                "Exception should be thrown for an invalid ticket");
    }

    @Test
    public void testSmartAttendantAssignsMultipleLots() {
        SmartAttendent smartAttendent = new SmartAttendent();
        ParkingLot lot1 = new ParkingLot(2);
        ParkingLot lot2 = new ParkingLot(3);

        assertDoesNotThrow(() -> {
            smartAttendent.assign(lot1);
            smartAttendent.assign(lot2);
        }, "No exception should be thrown when assigning multiple parking lots");
    }

    @Test
    public void testSmartAttendantParksCarsInLotWithMoreCapacity() {
        SmartAttendent smartAttendent = new SmartAttendent();
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
