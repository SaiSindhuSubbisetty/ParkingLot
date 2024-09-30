package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.ParkingLotIsFullException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PolicemanTest {

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