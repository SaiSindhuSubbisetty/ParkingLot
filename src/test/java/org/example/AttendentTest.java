package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttendentTest {

    @Test
    public void testAssignParkingLotToAttendent() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(1);

        assertDoesNotThrow(() -> attendent.assign(parkingLot));
    }

    @Test
    public void testAssignTwoParkingLotsToAttendent() {
        ParkingLot firstParkingLot = new ParkingLot(1);
        ParkingLot secondParkingLot = new ParkingLot(1);
        Attendent attendent = new Attendent();
        attendent.assign(firstParkingLot);

        assertDoesNotThrow(() -> attendent.assign(secondParkingLot));
    }

    @Test
    public void testCannotAssignSameParkingLotTwice() {
        ParkingLot parkingLot = new ParkingLot(1);
        Attendent attendent = new Attendent();
        attendent.assign(parkingLot);

        assertThrows(ParkingLotAlreadyAssignmentException.class, () -> attendent.assign(parkingLot));
    }

    @Test
    public void testAttendantParksCarAndReturnsTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);

        assertNotNull(ticket, "Attendant should return a valid parking ticket");
        assertTrue(parkingLot.isCarAlreadyParked(car), "Car should be parked in the parking lot");
    }

    @Test
    public void testAttendantUnparksCarWithTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);
        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "The car returned should be the same as the one that was parked");
        assertFalse(parkingLot.isCarAlreadyParked(car), "The car should no longer be parked in the parking lot");
    }

    @Test
    public void testAttendantUnparksCarWithInvalidTicket() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testUnparkedCarShouldMatchWithParkedCar() {
        Attendent attendent = new Attendent();
        ParkingLot firstparkingLot = new ParkingLot(1);
        ParkingLot secondparkingLot = new ParkingLot(1);
        attendent.assign(firstparkingLot);
        attendent.assign(secondparkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);  // parks in the first lot
        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "Unparked car should match the parked car");
    }

    @Test
    public void testUnparkCarThatIsNotInAssignedParkingLot() {
        Attendent attendent = new Attendent();
        ParkingLot firstparkingLot = new ParkingLot(1);
        ParkingLot secondparkingLot = new ParkingLot(1);
        attendent.assign(firstparkingLot);
        attendent.assign(secondparkingLot);

        Ticket invalidTicket = new Ticket();

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(invalidTicket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testAttendantCannotParkSameCarTwice() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);

        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car already assigned to this parking lot", exception.getMessage());
    }

    @Test
    public void testParkingSameInDifferentSlots() throws Exception {
        Attendent attendent = new Attendent();
        ParkingLot firstparkingLot = new ParkingLot(1);
        ParkingLot secondparkingLot = new ParkingLot(1);
        attendent.assign(firstparkingLot);
        attendent.assign(secondparkingLot);

        Car car = new Car("AP-1234", Color.RED);

        attendent.park(car);
        Exception exception = assertThrows(CarAlreadyParkedException.class, () -> attendent.park(car));
        assertEquals("Car already assigned to this parking lot", exception.getMessage());
    }

    @Test
    public void testUnParkCarInDifferentSlot() throws Exception {
        Attendent attendent = new Attendent();
        ParkingLot firstparkingLot = new ParkingLot(1);
        ParkingLot secondparkingLot = new ParkingLot(1);
        attendent.assign(firstparkingLot);
        attendent.assign(secondparkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);
        Car unparkedCar = attendent.unpark(ticket);

        assertEquals(car, unparkedCar, "The car returned should be the same as the one that was parked");
        assertFalse(firstparkingLot.isCarAlreadyParked(car), "The car should no longer be parked in the first parking lot");
        assertFalse(secondparkingLot.isCarAlreadyParked(car), "The car should no longer be parked in the second parking lot");
    }

    @Test
    public void testUnParkTheSameCarAgain() throws Exception {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(1);
        attendent.assign(parkingLot);

        Car car = new Car("AP-1234", Color.RED);

        Ticket ticket = attendent.park(car);
        attendent.unpark(ticket);

        Exception exception = assertThrows(CarNotFoundException.class, () -> attendent.unpark(ticket));
        assertEquals("Car not found in assigned parking lot", exception.getMessage());
    }

    @Test
    public void testAssignMultipleParkingLots() {
        Attendent attendent = new Attendent();
        ParkingLot firstparkingLot = new ParkingLot(2);
        ParkingLot secondparkingLot = new ParkingLot(3);

        attendent.assign(firstparkingLot);
        attendent.assign(secondparkingLot);

        assertDoesNotThrow(() -> attendent.assign(new ParkingLot(5)));
    }

    @Test
    public void testParkCarInNextAvailableSlot() {
        Attendent attendent = new Attendent();
        ParkingLot parkingLot = new ParkingLot(2);
        attendent.assign(parkingLot);

        Car firstcar = new Car("AP-1234", Color.RED);
        Car secondcar = new Car("AP-5678", Color.BLUE);

        Ticket ticket1 = attendent.park(firstcar);
        Ticket ticket2 = attendent.park(secondcar);

        assertNotNull(ticket1);
        assertNotNull(ticket2);
    }

    @Test
    public void testAssigningSameParkingLotToDifferentAttendent() throws Exception {
        ParkingLot ParkingLot = new ParkingLot(5);
        Attendent firstAttendant = new Attendent();
        Attendent secondAttendant = new Attendent();

        firstAttendant.assign(ParkingLot);
        assertDoesNotThrow(() -> {
            secondAttendant.assign(ParkingLot);
        });

    }

    @Test
    public void testAttendentParksCarsSequentially() {
        Attendent attendent = new Attendent();
        ParkingLot firstParkingLot = new ParkingLot(2);
        ParkingLot secondParkingLot = new ParkingLot(2);
        attendent.assign(firstParkingLot);
        attendent.assign(secondParkingLot);

        Car firstCar = new Car("AP-1234", Color.RED);
        Car secondCar = new Car("AP-5678", Color.BLUE);
        Car thirdCar = new Car("AP-9101", Color.GREEN);

        attendent.park(firstCar);
        attendent.park(secondCar);
        attendent.park(thirdCar);

        // Attendent parks in the first lot until full
        assertEquals(2, firstParkingLot.countParkedCars());
        assertEquals(1, secondParkingLot.countParkedCars());
    }


}
