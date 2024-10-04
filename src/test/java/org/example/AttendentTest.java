package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.example.Implementations.SmartNextLotStratergy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttendentTest {

    @Test
    public void testCreatingParkingLotOwner()  {
        Owner owner = new Owner();
        assertDoesNotThrow(() -> {
            owner.createParkingLot(2);
        });
    }

    @Test
    public void testCreatingParkingLotOfTotalSlotsAreZero()  {
        Owner owner = new Owner();
        assertThrows(Exception.class, () -> {
            owner.createParkingLot(0);
        });
    }


    @Test
    public void testAssignParkingLotToAttendent() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        assertDoesNotThrow(() -> owner.assign(parkingLot));
    }

    @Test
    public void testOwnerAssignMultipleParkingLotToSingleAttendent() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(3);
        ParkingLot secondParkingLot = owner.createParkingLot(3);
        Attendent attendant = new Attendent();

        assertDoesNotThrow(()->{
            owner.assignParkingLotToAttendent(attendant, firstParkingLot);
            owner.assignParkingLotToAttendent(attendant, secondParkingLot);
        });
    }

    @Test
    void testAssignParkingLot() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(parkingLot));
    }

    @Test
    void testAssignAParkingLotTwice() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assign(parkingLot));

        assertThrows(ParkingLotAlreadyAssignmentException.class, () -> owner.assign(parkingLot));
    }

    @Test
    void testParkIfCarIsAlreadyParked() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assignParkingLotToAttendent(attendent,parkingLot);

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

        owner.assignParkingLotToAttendent(attendent,firstParkingLot);
        owner.assignParkingLotToAttendent(attendent,secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);
        attendent.park(secondCar);

        assertThrows(CarAlreadyParkedException.class, () -> attendent.park(firstCar));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondCar() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot parkingLot = owner.createParkingLot(5);
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);
        Ticket ticket =  attendent.park(secondCar);

        assertThrows(InvalidTicketException.class, () -> attendent.unpark(ticket));
    }

    @Test
    void testUnparkIfTicketIsInvalidForSecondParkingLot() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot firstParkingLot = owner.createParkingLot(1);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        owner.assignParkingLotToAttendent(attendent,firstParkingLot);
        owner.assignParkingLotToAttendent(attendent,secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        attendent.park(firstCar);

        Ticket ticket =  attendent.park(secondCar);

        assertThrows(InvalidTicketException.class, () -> attendent.unpark(ticket));
    }

    @Test
    public void testOwnerAssignMultipleParkingLotToMultipleAttendents() throws Exception {
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
    public void testOwnerAssignParkingLotToself() throws Exception {
        Owner owner = new Owner();

        ParkingLot firstParkingLot = owner.createParkingLot(2);
        ParkingLot secondParkingLot = owner.createParkingLot(3);

        owner.assignParkingLotToSelf(secondParkingLot);
        assertDoesNotThrow(() -> {
            owner.assignParkingLotToSelf(firstParkingLot);
        });
    }

    @Test
    void testAssignTwoParkingLotsToSameAttendent() throws Exception {
        Owner owner = new Owner();
        Attendent attendent = new Attendent();

        ParkingLot firstParkingLot = owner.createParkingLot(5);
        ParkingLot secondParkingLot = owner.createParkingLot(5);

        assertDoesNotThrow(() -> owner.assignParkingLotToAttendent(attendent,firstParkingLot));
        assertDoesNotThrow(() -> owner.assignParkingLotToAttendent(attendent,secondParkingLot));
    }

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
    public void testParkingLotOwnerUnParkTheCar() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstParkingLot = owner.createParkingLot(2);
        Car firstCar = new Car("AP-5678", Color.BLUE);
        owner.assignParkingLotToSelf(firstParkingLot);

        Ticket ticket = owner.park(firstCar);
        Car car = owner.unpark(ticket);
        assertEquals(firstCar, car);
    }
    @Test
    public void testattendentParksCarAndReturnsTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "attendent should return a valid parking ticket");
        assertTrue(parkingLot.isCarAlreadyParked(car), "Car should be parked in the parking lot");
    }

    @Test
    public void testattendentCannotParkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car is already parked", exception.getMessage());
    }

    @Test
    public void testUnparkCarWithValidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent,parkingLot);

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
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        Ticket invalidTicket = new Ticket(); 

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testCannotUnparkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);

        Attendent attendent = new Attendent();
        owner.assignParkingLotToAttendent(attendent,parkingLot);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = attendent.park(car);

        attendent.unpark(ticket); // First unpark succeeds

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }
    

    @Test
    public void testAttendentParksCarsSequentially() throws Exception {
        Owner owner = new Owner();

        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);

        Attendent attendent = new Attendent();

        owner.assignParkingLotToAttendent(attendent,firstLot);
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
    public void testNormalAttendentThrowsWhenAllLotsFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot = owner.createParkingLot(1);
        Attendent attendent = new Attendent();

        owner.assignParkingLotToAttendent(attendent,lot);

        Car firstCar = new Car("AP-1234", Color.RED);
        attendent.park(firstCar); // Fills the lot

        Car secondCar = new Car("AP-5678", Color.BLUE);

        assertThrows(ParkingLotIsFullException.class, () -> attendent.park(secondCar),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testNormalAttendentThrowsWhenCarAlreadyParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot = owner.createParkingLot(2);
        Attendent attendent = new Attendent();

        owner.assignParkingLotToAttendent(attendent,lot);

        Car car = new Car("AP-1234", Color.RED);
        attendent.park(car); 

        assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car),
                "Exception should be thrown when the same car is parked again");
    }

    @Test
    public void testSmartAttendentParksCarInLotWithFewestCars() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent,firstLot);
        owner.assignParkingLotToAttendent(smartAttendent,secondLot);

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

        owner.assignParkingLotToAttendent(smartAttendent,firstLot);
        owner.assignParkingLotToAttendent(smartAttendent,secondLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        smartAttendent.park(firstCar);

        Car secondCar = new Car("AP-5678", Color.BLUE);
        smartAttendent.park(secondCar);

        Car thirdCar = new Car("AP-9012", Color.GREEN);
        assertThrows(ParkingLotIsFullException.class, () -> smartAttendent.park(thirdCar),
                "Exception should be thrown when trying to park in a full lot");
    }

    @Test
    public void testSmartAttendentThrowsWhenNoParkingLotAssigned() {
        Owner owner = new Owner();
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());
        Car car = new Car("AP-1234", Color.RED);

        assertThrows(NoParkingLotAssignedException.class, () -> smartAttendent.park(car),
                "Exception should be thrown when no parking lot is assigned");
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

    @Test
    public void testSmartAttendentUnparksCarWithValidTicket() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(2);
        ParkingLot secondLot = owner.createParkingLot(2);
        Attendent smartAttendent = new Attendent(new SmartNextLotStratergy());

        owner.assignParkingLotToAttendent(smartAttendent,firstLot);
        owner.assignParkingLotToAttendent(smartAttendent,secondLot);

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

        owner.assignParkingLotToAttendent(smartAttendent,firstLot);

        Ticket invalidTicket = new Ticket();

        assertThrows(CarNotFoundException.class, () -> smartAttendent.unpark(invalidTicket),
                "Exception should be thrown for an invalid ticket");
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


}
