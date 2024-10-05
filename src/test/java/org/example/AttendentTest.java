package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendentTest {

    //Tests for Assign() in Attendent
    @Test
    public void testAssignParkingLotToAttendent() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();

        assertDoesNotThrow(() -> owner.assignParkingLotToAttendent(attendent,parkingLot));
    }

    @Test
    void testAssignAParkingLotTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        Attendent attendent = new Attendent();

        assertDoesNotThrow(() -> owner.assignParkingLotToAttendent(attendent,parkingLot));

        assertThrows(ParkingLotAlreadyAssignmentException.class, () -> owner.assignParkingLotToAttendent(attendent,parkingLot));
    }
    
    @Test
    public void testAssignMultipleParkingLotToSingleAttendent() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        ParkingLot thirdParkingLot = owner.createParkingLot(3);
        ParkingLot fourthParkingLot = owner.createParkingLot(3);

        Attendent Attendent = new Attendent();

        assertDoesNotThrow(() -> {
            owner.assignParkingLotToAttendent(Attendent, firstParkingLot);
            owner.assignParkingLotToAttendent(Attendent, secondParkingLot);
            owner.assignParkingLotToAttendent(Attendent, thirdParkingLot);
            owner.assignParkingLotToAttendent(Attendent, fourthParkingLot);
        });
    }

    @Test
    public void testAssignMultipleParkingLotToMultipleAttendents() throws Exception {
        Owner owner = new Owner();
        Attendent firstAttendent = new Attendent();
        Attendent secondAttendent = new Attendent();
        Attendent thirdAttendent = new Attendent();
        Attendent fourthAttendent = new Attendent();

        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        ParkingLot thirdParkingLot = owner.createParkingLot(3);
        ParkingLot fourthParkingLot = owner.createParkingLot(3);

        assertDoesNotThrow(() -> {
            owner.assignParkingLotToAttendent(firstAttendent, firstParkingLot);
            owner.assignParkingLotToAttendent(secondAttendent, secondParkingLot);
            owner.assignParkingLotToAttendent(thirdAttendent, thirdParkingLot);
            owner.assignParkingLotToAttendent(fourthAttendent, fourthParkingLot);
        });
    }

    //Tests for Park() in Attendent

    @Test
    void testParkIfCarIsAlreadyParked() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
    }

    @Test
    void testParkIfCarIsAlreadyParkedInAnotherParkingLot() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        owner.assignParkingLotToAttendent(attendent, firstParkingLot);
        owner.assignParkingLotToAttendent(attendent, secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);
        attendent.park(secondCar);

        assertThrows(CarAlreadyParkedException.class, () -> attendent.park(firstCar));
    }

    @Test
    public void testAttendentParksCarAndReturnsTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "attendent should return a valid parking ticket");
        assertTrue(parkingLot.isCarAlreadyParked(car), "Car should be parked in the parking lot");
    }

    @Test
    public void testAttendentCannotParkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car already assigned to this parking lot", exception.getMessage());
    }

    @Test
    public void testAttendentParksCarsSequentially() throws Exception {
        Owner owner = new Owner();

        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);

        Attendent attendent = new Attendent();

        owner.assignParkingLotToAttendent(attendent, firstLot);
        owner.assignParkingLotToAttendent(attendent, secondLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        Car thirdCar = new Car("AP-9101", Color.GREEN);

        attendent.park(firstCar);
        attendent.park(secondCar);
        attendent.park(thirdCar);

        assertEquals(1, firstLot.countParkedCars(), "First lot should have 2 cars");
        assertEquals(2, secondLot.countParkedCars(), "Second lot should have 1 car");
    }

    @Test
    void testParkIfFirstParkingLotIsFull() throws Exception {
        Owner owner = new Owner();
        Attendent Attendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);

        owner.assignParkingLotToAttendent(Attendent, firstParkingLot);
        owner.assignParkingLotToAttendent(Attendent, secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        assertDoesNotThrow(() -> Attendent.park(firstCar));
        assertDoesNotThrow(() -> Attendent.park(secondCar));
    }

    @Test
    void testParkIfAllParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        Attendent Attendent = new Attendent();
        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(1);

        owner.assignParkingLotToAttendent(Attendent, firstParkingLot);
        owner.assignParkingLotToAttendent(Attendent, secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        Car thirdCar = new Car("AP-1432", Color.YELLOW);

        Attendent.park(firstCar);
        Attendent.park(secondCar);

        assertThrows(ParkingLotIsFullException.class, () -> Attendent.park(thirdCar));
    }


    //Tests for Unpark() in Attendent

    @Test
    void testUnparkIfTicketIsInvalidForSecondCar() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);
        Ticket ticket = attendent.park(secondCar);

        attendent.unpark(ticket);

        assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondParkingLot() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        owner.assignParkingLotToAttendent(attendent, firstParkingLot);
        owner.assignParkingLotToAttendent(attendent, secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);

        Ticket ticket = attendent.park(secondCar);

        assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
    }


    @Test
    public void testUnparkCarWithValidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
        assertFalse(parkingLot.isCarAlreadyParked(car), "Car should no longer be parked in the lot");
    }

    @Test
    public void testUnparkCarWithInvalidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);

        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in the slot.", exception.getMessage());
    }

    @Test
    public void testCannotUnparkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);

        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent, parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        attendent.unpark(ticket);

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
        assertEquals("Car not found in the slot.", exception.getMessage());
    }

    // Tests for Assign() for Attendent with SmartNextLotStrategy

    @Test
    public void testSmartAttendentThrowsWhenNoParkingLotAssigned() {
        Owner owner = new Owner();
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        Car car = new Car("AP-1234", Color.RED);

        assertThrows(NoParkingLotAssignedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when no parking lot is assigned");
    }

    @Test
    public void testSmartAttendentAssignsMultipleLots() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(3);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        assertDoesNotThrow(() -> {
            owner.assign(firstLot);
            owner.assign(secondLot);
        }, "No exception should be thrown when assigning multiple parking lots");
    }

    // Tests for Park() for Attendent with SmartNextLotStrategy
    @Test
    public void testSmartAttendentParksCarInLotWithFewestCars() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent, firstLot);
        owner.assignParkingLotToAttendent(smartAttendent, secondLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        smartAttendent.park(firstCar);

        Car secondCar = new Car("AP-5678", Color.BLUE);
        Ticket secondticket = smartAttendent.park(secondCar);

        assertNotNull(secondticket, "Ticket should not be null for secondCar");
        assertTrue(secondLot.isCarAlreadyParked(secondCar), "secondCar should be parked in secondLot");
    }

    @Test
    public void testSmartAttendentThrowsWhenAllLotsFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(1);
        ParkingLot secondLot = owner.createParkingLot(1);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent, firstLot);
        owner.assignParkingLotToAttendent(smartAttendent, secondLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        smartAttendent.park(firstCar);

        Car secondCar = new Car("AP-5678", Color.BLUE);
        smartAttendent.park(secondCar);

        Car thirdCar = new Car("AP-9012", Color.GREEN);
        assertThrows(ParkingLotIsFullException.class, () -> smartAttendent.park(thirdCar),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testSmartAttendentCannotParkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        owner.assignParkingLotToAttendent(smartAttendent, lot);

        Car car = new Car("AP-1234", Color.RED);
        smartAttendent.park(car); // Park the car

        assertThrows(CarAlreadyParkedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when trying to park the same car again");
    }

    // Tests for Unpark() for Attendent with SmartNextLotStrategy

    @Test
    public void testSmartAttendentUnparksCarWithValidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent, firstLot);
        owner.assignParkingLotToAttendent(smartAttendent, secondLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = smartAttendent.park(car);

        Car unparkedCar = smartAttendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
        assertFalse(firstLot.isCarAlreadyParked(car), "The car should no longer be parked in the lot");
    }

    @Test
    public void testSmartAttendentUnparksCarWithInvalidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent, firstLot);

        Ticket invalidTicket = new Ticket();

        assertThrows(CarNotFoundException.class, () -> smartAttendent.unpark(invalidTicket),
                "Exception should be thrown for an invalid ticket");
    }

}
