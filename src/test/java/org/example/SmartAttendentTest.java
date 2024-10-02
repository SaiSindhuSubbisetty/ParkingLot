package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
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
        Ticket ticket2 = smartAttendent.park(car2); // Should park in lot2 (fewest cars)

        assertNotNull(ticket2, "Ticket should not be null for car2");
        assertTrue(lot2.isCarAlreadyParked(car2), "Car2 should be parked in lot2");
    }

    @Test
    public void testSmartAttendantThrowsWhenAllLotsFull() {
        SmartAttendent smartAttendent = new SmartAttendent();
        ParkingLot lot = new ParkingLot(1);
        smartAttendent.assign(lot);

        Car car1 = new Car("AP-1234", Color.RED);
        smartAttendent.park(car1); // Fills the lot

        Car car2 = new Car("AP-5678", Color.BLUE);

        assertThrows(SelectedLotIsFullException.class, () -> smartAttendent.park(car2),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testSmartAttendantThrowsWhenNoParkingLotAssigned() {
        SmartAttendent smartAttendent = new SmartAttendent();
        Car car = new Car("AP-1234", Color.RED);

        assertThrows(ParkingLotIsFullException.NoParkingLotAssignedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when no parking lot is assigned");
    }
}
