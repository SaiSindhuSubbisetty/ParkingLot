package org.example;

import org.example.Enums.Color;
import org.example.Enums.OwnerType;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.Car;
import org.example.Implementations.Owner;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.Ticket;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

    @Test
    public void testOwnerCanAssistWithParking() {
        Owner owner = new Owner(OwnerType.SMART);
        ParkingLot parkingLot = new ParkingLot(2);

        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket);
    }

    @Test
    public void testOwnerAssignsParkingLotToAttendant() {
        Owner owner = new Owner(OwnerType.SMART);
        ParkingLot lot = new ParkingLot(2);

        assertDoesNotThrow(() -> owner.addOwnedParkingLot(lot),
                "Owner should be able to assign parking lots to attendant");
    }

    @Test
    public void testOwnerParksCarThroughAssignedAttendant() {
        Owner owner = new Owner(OwnerType.NORMAL);
        ParkingLot lot = new ParkingLot(1);
        owner.addOwnedParkingLot(lot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = owner.park(car);

        assertNotNull(ticket, "Owner's attendant should return a valid parking ticket");
        assertTrue(lot.isCarAlreadyParked(car), "Car should be parked in the assigned parking lot");
    }

    @Test
    public void testOwnerThrowsWhenParkingLotFull() {
        Owner owner = new Owner(OwnerType.NORMAL);
        ParkingLot lot = new ParkingLot(1);
        owner.addOwnedParkingLot(lot);

        Car car1 = new Car("AP-1234", Color.RED);
        owner.park(car1); // Fill the lot

        Car car2 = new Car("AP-5678", Color.BLUE);

        assertThrows(ParkingLotIsFullException.class, () -> owner.park(car2),
                "Exception should be thrown when parking lot is full");
    }

    @Test
    public void testOwnerUnparksCarThroughAssignedAttendant() {
        Owner owner = new Owner(OwnerType.NORMAL);
        ParkingLot lot = new ParkingLot(2);
        owner.addOwnedParkingLot(lot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = owner.park(car);

        Car unparkedCar = owner.unpark(ticket);
        assertEquals(car, unparkedCar, "The car unparked should match the one that was parked");
        assertFalse(lot.isCarAlreadyParked(car), "Car should no longer be parked after un-parking");
    }

    @Test
    public void testOwnerNotifiedWhenParkingLotFull() {
        Owner owner = new Owner(OwnerType.NORMAL);
        ParkingLot parkingLot = new ParkingLot(1);

        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        owner.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> owner.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testOwnerNotifiedWhenParkingLotAvailable() throws ParkingLotAlreadyAssignmentException {
        Owner owner = new Owner(OwnerType.NORMAL);
        ParkingLot parkingLot = new ParkingLot(1);
        owner.addOwnedParkingLot(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = owner.park(car);

        owner.unpark(ticket);

        assertFalse(parkingLot.isFull(), "Owner should be notified when parking lot has available slots");
    }


}
