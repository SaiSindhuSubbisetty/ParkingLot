package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {


    @Test
    public void testExceptionNewParkingLotIsEmpty() {
        Owner owner = new Owner();
        assertThrows(IllegalArgumentException.class, () -> owner.createParkingLot(0));
    }

    @Test
    public void testExceptionForNegativeParkingSlots() {
        Owner owner = new Owner();
        assertThrows(IllegalArgumentException.class, () -> owner.createParkingLot(-1));
    }

    @Test
    public void testCreateParkingLotWith5Slots() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);

        assertNotNull(parkingLot);
    }

    @Test
    public void testCannotParkSameCarTwice() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Car car = new Car("AP-1234", Color.RED);

        parkingLot.park(car);
        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(car));

        assertEquals("Car is already parked", exception.getMessage());
    }

    @Test
    public void testParkingLotWithOneSlotIsFullWhenCarParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
    }

    @Test
    public void testParkingLotWithTwoSlotsIsNotFullWhenOneCarParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(2);
        Car car = new Car("AP-1431", Color.BLUE);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
        assertFalse(parkingLot.isFull());
    }

    @Test
    public void testParkWith5Slots() throws Exception {
        Owner owner = new Owner();
        Car car = new Car("AP-9876", Color.BLACK);
        ParkingLot parkingLot = owner.createParkingLot(5);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
    }

    @Test
    public void testParkInFullParkingLot() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        parkingLot.park(firstCar);
        Exception exception = assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(secondCar));

        assertEquals("Parking lot is full.", exception.getMessage());
    }

    @Test
    public void testParkInNearestAvailableSlot() throws Exception {
        Owner owner = new Owner();
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.BLUE);
        ParkingLot parkingLot = owner.createParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertTrue(parkingLot.isCarAlreadyParked(firstCar));
        assertTrue(parkingLot.isCarAlreadyParked(secondCar));
    }

    @Test
    public void testParkInNearestAvailableSlotAfterUnparking() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        Car thirdCar = new Car("AP-9999", Color.GREEN);
        Ticket firstCarTicket = parkingLot.park(firstCar);

        parkingLot.park(secondCar);
        parkingLot.unpark(firstCarTicket);
        parkingLot.park(thirdCar);

        assertTrue(parkingLot.isCarAlreadyParked(thirdCar));
    }

    @Test
    public void testUnparkCarThatIsNotParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> parkingLot.unpark(invalidTicket));

        assertEquals("Car not found in the parking lot.", exception.getMessage());
    }

    @Test
    public void testCountCarsByColor() throws Exception {
        Owner owner = new Owner();
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.RED);
        Car thirdCar = new Car("AP-0001", Color.BLUE);
        ParkingLot parkingLot = owner.createParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        assertEquals(2, parkingLot.countCarsByColor(Color.RED));
    }

    @Test
    public void testIsCarWithRegistrationNumberParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Car car = new Car("AP-1234", Color.RED);

        parkingLot.park(car);
        assertTrue(parkingLot.isCarWithRegistrationNumberParked("AP-1234"));
    }

    @Test
    public void testIsCarWithoutRegistrationNumberCannotParked() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Car car = new Car(null, Color.RED);  // Car without a registration number

        Exception exception = assertThrows(CarNeedsRegistrationNumberException.class, () -> parkingLot.isCarWithRegistrationNumberParked(null));

        assertEquals("Car needs registration number.", exception.getMessage());
    }

    @Test
    public void testPolicemanNotifiedWhenParkingLotAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(5);
        Policeman policeman = new Policeman();
        parkingLot.registerNotifiable(policeman);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);
        parkingLot.unpark(ticket);

        assertFalse(parkingLot.isFull());
    }
}
