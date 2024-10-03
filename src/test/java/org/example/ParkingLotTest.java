package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNeedsRegistrationNumberException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ParkingLotTest {

    @Test
    public void testExceptionNewParkingLotIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(0));
    }

    @Test
    public void testExceptionForNegativeParkingSlots() {
        assertThrows(IllegalArgumentException.class, () -> new ParkingLot(-1));
    }

    @Test
    public void testCreateParkingLotWith5Slots() {
        ParkingLot parkingLot = new ParkingLot(5);

        assertNotNull(parkingLot);
    }

    @Test
    public void testCannotParkSameCarTwice() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-1234", Color.RED);

        parkingLot.park(car);
        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> parkingLot.park(car));

        assertEquals("Car is already parked", exception.getMessage());
    }

    @Test
    public void testParkingLotWithOneSlotIsFullWhenCarParked() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
    }

    @Test
    public void testParkingLotWithTwoSlotsIsNotFullWhenOneCarParked() {
        ParkingLot parkingLot = new ParkingLot(2);
        Car car = new Car("AP-1431", Color.BLUE);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
    }


    @Test
    public void testParkWith5Slots() {
        Car car = new Car("AP-9876", Color.BLACK);
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket ticket = parkingLot.park(car);

        assertNotNull(ticket);
        assertTrue(parkingLot.isCarAlreadyParked(car));
    }

    @Test
    public void testParkInFullParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        parkingLot.park(firstCar);
        Exception exception = assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(secondCar));

        assertEquals("Parking lot is full", exception.getMessage());
    }

    @Test
    public void testParkInNearestAvailableSlot() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.BLUE);
        ParkingLot parkingLot = new ParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);

        assertTrue(parkingLot.isCarAlreadyParked(firstCar));
        assertTrue(parkingLot.isCarAlreadyParked(secondCar));
    }

    @Test
    public void testParkInNearestAvailableSlotAfterUnparking() {
        ParkingLot parkingLot = new ParkingLot(5);
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
    public void testUnparkCarThatIsNotParked() {
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket invalidTicket = new Ticket();  // Ticket for an empty slot

        Exception exception = assertThrows(CarNotFoundException.class, () -> parkingLot.unpark(invalidTicket));

        assertEquals("Car not found in the parking lot", exception.getMessage());
    }

    @Test
    public void testUnparkCarFromEmptyParkingLot() {
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket invalidTicket = new Ticket();  // Empty parking lot

        Exception exception = assertThrows(CarNotFoundException.class, () -> parkingLot.unpark(invalidTicket));

        assertEquals("Car not found in the parking lot", exception.getMessage());
    }

    @Test
    public void testUnpark() {
        Car car = new Car("AP-1234", Color.RED);
        ParkingLot parkingLot = new ParkingLot(5);
        Ticket ticket = parkingLot.park(car);
        Car unparkedCar = parkingLot.unpark(ticket);

        assertEquals(car, unparkedCar);  // Ensure the correct car is returned
    }


    @Test
    public void testCountCarsByRedColorIsNotFoundInParkingLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        int count = parkingLot.countCarsByColor(Color.RED);

        assertEquals(0, count);
    }

    @Test
    public void testCountCarsByColorNotPresent() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-1234", Color.BLUE);

        parkingLot.park(car);
        assertEquals(0, parkingLot.countCarsByColor(Color.YELLOW));
    }

    @Test
    public void testCountCarsByColor() {
        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-9999", Color.RED);
        Car thirdCar = new Car("AP-0001", Color.BLUE);
        ParkingLot parkingLot = new ParkingLot(5);

        parkingLot.park(firstCar);
        parkingLot.park(secondCar);
        parkingLot.park(thirdCar);

        assertEquals(2, parkingLot.countCarsByColor(Color.RED));
    }

    @Test
    public void testisCarAlreadyParkedForNonParkedCar() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-1432", Color.YELLOW);

        assertFalse(parkingLot.isCarAlreadyParked(car));  // Car is not parked
    }

    @Test
    public void testIsParkingLotFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = new Car("AP-4321", Color.BLUE);

        parkingLot.park(car);
        assertTrue(parkingLot.isFull());  // Parking lot is full after one car is parked
    }

    @Test
    public void testIsParkingLotNotFull() {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-9876", Color.GREEN);

        parkingLot.park(car);
        assertFalse(parkingLot.isFull());  // Parking lot is not full
    }

    @Test
    public void testIsCarWithRegistrationNumberParked() throws CarNeedsRegistrationNumberException {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car("AP-1234", Color.RED);

        parkingLot.park(car);

        assertTrue(parkingLot.isCarWithRegistrationNumberParked("AP-1234"));
    }

    @Test
    public void testIsCarWithoutRegistrationNumberCannotParked() throws CarNeedsRegistrationNumberException {
        ParkingLot parkingLot = new ParkingLot(5);
        Car car = new Car(null, Color.RED);  // Car without a registration number

        Exception exception = assertThrows(CarNeedsRegistrationNumberException.class, () -> {
            parkingLot.isCarWithRegistrationNumberParked(null);
        });

        assertEquals("Car needs registration number.", exception.getMessage());
    }

    @Test
    public void testAssignMultipleAssistantsToParkingLot() {
        ParkingLot parkingLot = new ParkingLot(2);
        Attendent attendent1 = new Attendent();
        Attendent attendent2 = new Attendent();

        parkingLot.addAssistant(attendent1);
        parkingLot.addAssistant(attendent2);

        assertDoesNotThrow(() -> parkingLot.addAssistant(new Attendent()));
    }

    @Test
    public void testParkCarInNextAvailableSlot() {
        ParkingLot parkingLot = new ParkingLot(2);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);

        Ticket ticket1 = parkingLot.park(firstCar);
        Ticket ticket2 = parkingLot.park(secondCar);

        assertNotNull(ticket1);
        assertNotNull(ticket2);
    }


    @Test
    public void testPolicemanNotifiedWhenParkingLotFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        Policeman policeman = new Policeman();
        parkingLot.addPoliceman(policeman);

        Car car = new Car("AP-1234", Color.RED);
        parkingLot.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testPolicemanNotifiedWhenParkingLotAvailable() {
        ParkingLot parkingLot = new ParkingLot(1);
        Policeman policeman = new Policeman();
        parkingLot.addPoliceman(policeman);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);
        parkingLot.unpark(ticket);

        assertDoesNotThrow(() -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }
}