package org.example;


import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {


    @Test
    public void testCreatingOwner() throws Exception {
        Owner owner = new Owner();
        assertDoesNotThrow(() -> {
            owner.createParkingLot(2);
        });
    }

    @Test
    public void testCreatingParkingLotWithZeroTotalSlots() throws Exception {
        Owner owner = new Owner();
        assertThrows(Exception.class, () -> {
            owner.createParkingLot(0);
        });
    }

    @Test
    public void testOwnerassignParkingLotToAttendent() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendent(attendent, firstParkingLot);
        });
    }

    @Test
    public void testOwnerAssignMultipleParkingLotToSingleAttendant() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendent(attendent, firstParkingLot);
            owner.assignParkingLotToAttendent(attendent, secondParkingLot);
        });
    }

    @Test
    public void testOwnerAssignMultipleParkingLotToMultipleAttendant() throws Exception {
        Owner owner = new Owner();
        Attendent firstAttendant = new Attendent();
        Attendent secondAttendant = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendent(firstAttendant, firstParkingLot);
            owner.assignParkingLotToAttendent(secondAttendant, secondParkingLot);
        });
    }

    @Test
    public void testExceptionWhenOwnerAssignNotOwnedParkingLot() throws Exception {
        Owner owner = new Owner();
        Owner otherOwner = new Owner();
        Attendent firstAttendant = new Attendent();
        ParkingLot firstParkingLot = otherOwner.createParkingLot(3);

        assertThrows(notOwnedParkingLotException.class, () -> {
            owner.assignParkingLotToAttendent(firstAttendant, firstParkingLot);
        });
    }

    @Test
    public void testSingleAttendantCannotHaveMultipleOwners() throws Exception {
        Owner owner = new Owner();
        Owner otherOwner = new Owner();
        Attendent firstAttendant = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = otherOwner.createParkingLot(3);

        owner.assignParkingLotToAttendent(firstAttendant, firstParkingLot);
        otherOwner.assignParkingLotToAttendent(firstAttendant, secondParkingLot);

    }

    @Test
    public void testOwnerassignParkingLotToAttendentToItself() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        owner.assignParkingLotToSelf(secondParkingLot);
        assertDoesNotThrow(() -> {
            owner.assignParkingLotToSelf(firstParkingLot);
        });
    }

    @Test
    public void testOwnerExceptionWhenNoParkingLotAssignedToIt() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("AP-5678", Color.BLUE);

        assertThrows(NoParkingLotAssignedException.class, () -> {
            owner.park(firstCar);
        });
    }

    @Test
    public void testParkingOwnerParkingACar() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("AP-5678", Color.BLUE);

        owner.assignParkingLotToSelf(firstParkingLot);
        assertDoesNotThrow(() -> {
            owner.park(firstCar);
        });
    }

    @Test
    public void testOwnerUnParkTheCar() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("AP-5678", Color.BLUE);
        owner.assignParkingLotToSelf(firstParkingLot);

        Ticket ticket = owner.park(firstCar);
        Car actualCar = owner.unpark(ticket);

        assertEquals(firstCar, actualCar);
    }

    @Test
    public void testOwnerUnParkingCarParkedInNonAssignedParkingLot() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        Car firstCar = new Car("AP-5678", Color.BLUE);
        Attendent attendant = new Attendent(new SmartNextLotStratergy());
        attendant.assign(secondParkingLot);
        owner.assignParkingLotToSelf(firstParkingLot);

        Ticket ticket = attendant.park(firstCar);

        assertThrows(CarNotFoundException.class, () -> {
            owner.unpark(ticket);
        });
    }

    @Test
    public void testParkingOwnerParkingCarInFullParkingLot() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        Car firstCar = new Car("AP-5678", Color.BLUE);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        owner.assignParkingLotToSelf(firstParkingLot);

        owner.park(firstCar);

        assertThrows(ParkingLotIsFullException.class, () -> {
            owner.park(secondCar);
        });
    }



}