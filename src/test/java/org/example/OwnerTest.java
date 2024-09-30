package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {
    @Test
    public void testOwnerCanAssistWithParking() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(2);

        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket);
    }

    @Test
    public void testOwnerNotifiedWhenParkingLotFull() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(1);

        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        owner.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> owner.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testOwnerNotifiedWhenParkingLotAvailable() {
        Owner owner = new Owner();
        ParkingLot parkingLot = new ParkingLot(1);

        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = owner.park(car);

        owner.unpark(ticket);

        assertDoesNotThrow(() -> owner.park(new Car("AP-5678", Color.BLUE)));
    }

}
