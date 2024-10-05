package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.example.Implementations.SmartNextLotStratergy;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    public void testExceptionWhenOwnerAssignNotOwnedParkingLot() throws Exception {
        Owner owner = new Owner();
        Owner otherOwner = new Owner();
        Attendent firstAttendent = new Attendent();
        ParkingLot firstParkingLot = otherOwner.createParkingLot(3);

        assertThrows(notOwnedParkingLotException.class, () -> {
            owner.assignParkingLotToAttendent(firstAttendent, firstParkingLot);
        });
    }

    // Test for assignParkingLotToAttendent()
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
    public void testExceptionAssignParkingLotIsNotOwnedByOwner() throws Exception {
        Owner owner = new Owner();
        Owner secondOwner = new Owner();
        Attendent firstAttendant = new Attendent();

        ParkingLot firstParkingLot = secondOwner.createParkingLot(3);

        assertThrows(notOwnedParkingLotException.class, () -> {
            owner.assignParkingLotToAttendent(firstAttendant, firstParkingLot);
        });
    }

    @Test
    public void testOwnerAssignMultipleParkingLotToSingleAttendent() throws Exception {
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
    public void testOwnerAssignMultipleParkingLotToMultipleAttendent() throws Exception {
        Owner owner = new Owner();
        Attendent firstAttendent = new Attendent();
        Attendent secondAttendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendent(firstAttendent, firstParkingLot);
            owner.assignParkingLotToAttendent(secondAttendent, secondParkingLot);
        });
    }

    @Test
    public void testSingleAttendentCannotHaveMultipleOwners() throws Exception {
        Owner owner = new Owner();
        Owner otherOwner = new Owner();
        Attendent firstAttendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = otherOwner.createParkingLot(3);

        owner.assignParkingLotToAttendent(firstAttendent, firstParkingLot);
        otherOwner.assignParkingLotToAttendent(firstAttendent, secondParkingLot);
    }

    // Test for assignParkingLotToSelf()
    @Test
    public void testOwnerAssignParkingLotToSelf() throws Exception {
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

    // Tests for park() in Owner
    @Test
    public void testOwnerParkingACar() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("AP-5678", Color.BLUE);

        owner.assignParkingLotToSelf(firstParkingLot);
        assertDoesNotThrow(() -> {
            owner.park(firstCar);
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

        assertThrows(CarAlreadyParkedException.class, () -> {
            owner.park(secondCar);
        });
    }

    // Tests for unpark() in Owner

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
        Attendent Attendent = new Attendent(new SmartNextLotStratergy());

        Attendent.assign(secondParkingLot);
        owner.assignParkingLotToSelf(firstParkingLot);

        Ticket ticket = Attendent.park(firstCar);

        assertThrows(CarNotFoundException.class, () -> {
            owner.unpark(ticket);
        });
    }

    // Tests for notifyFull() in Owner

    @Test
    public void testOwnerNotifiedWhenParkingLotFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        owner.assignParkingLotToSelf(parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        owner.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testOwnerNotifyFullAllParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(1);
        Owner ownerSpy = spy(owner);

        firstlot.registerNotifiable(ownerSpy);
        secondlot.registerNotifiable(ownerSpy);

        firstlot.park(new Car("AP-1234", Color.RED));
        secondlot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        assertThrows(ParkingLotIsFullException.class, () -> secondlot.park(new Car("AP-9998", Color.YELLOW)));
        
        verify(ownerSpy).notifyFull(firstlot.getParkingLotId());
        verify(ownerSpy).notifyFull(secondlot.getParkingLotId());
    }

    @Test
    public void testOwnerNotifyFullSomeParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(2);
        Owner ownerSpy = spy(owner);

        firstlot.registerNotifiable(ownerSpy);
        secondlot.registerNotifiable(ownerSpy);

        firstlot.park(new Car("AP-1234", Color.RED));  // firstlot is full
        secondlot.park(new Car("AP-5678", Color.BLUE));  // secondlot still has space

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        verify(ownerSpy).notifyFull(firstlot.getParkingLotId());

        verify(ownerSpy, Mockito.never()).notifyFull(secondlot.getParkingLotId());
    }

    @Test
    public void testNotifyFullWhenParkingLotIsFull() throws Exception {
        Owner owner = spy(new Owner());
        ParkingLot parkingLot = owner.createParkingLot(1);

        Car firstCar = new Car("UP81", Color.BLUE);

        owner.assignParkingLotToSelf(parkingLot);

        verify(owner,times(0)).notifyFull(anyInt());

        owner.park(firstCar);

        verify(owner,times(1)).notifyFull(anyInt());
    }

    // Tests for notifyAvailable() in Owner

    @Test
    public void testOwnerNotifyFullSomeParkingLotsAreAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(2);
        Owner ownerSpy = spy(owner);

        firstlot.registerNotifiable(ownerSpy);
        secondlot.registerNotifiable(ownerSpy);


        Ticket ticket1 = firstlot.park(new Car("AP-1234", Color.RED));  
        Ticket ticket2 = secondlot.park(new Car("AP-5678", Color.BLUE)); 

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        verify(ownerSpy).notifyFull(firstlot.getParkingLotId());

        firstlot.unpark(ticket1);
        verify(ownerSpy).notifyAvailable(firstlot.getParkingLotId());

        assertDoesNotThrow(() -> firstlot.park(new Car("AP-8888", Color.BLACK)));
    }

    @Test
    public void testOwnerNotifiedWhenParkingLotIsAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);

        Owner ownerSpy = spy(owner);

        parkingLot.registerNotifiable(ownerSpy);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        parkingLot.unpark(ticket);

        verify(ownerSpy).notifyAvailable(parkingLot.getParkingLotId());
        
        assertDoesNotThrow(() -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

}