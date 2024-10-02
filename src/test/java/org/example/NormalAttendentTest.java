package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NormalAttendentTest {

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
