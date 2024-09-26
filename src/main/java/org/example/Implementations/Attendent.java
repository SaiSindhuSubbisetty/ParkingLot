package org.example.Implementations;

import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.Exceptions.ParkingLotIsFullException;

import java.util.LinkedList;

public class Attendent {
    private final LinkedList<ParkingLot> parkingLots = new LinkedList<>();

    public void assign(ParkingLot parkingLot) {
        if (parkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        parkingLots.add(parkingLot);
    }

    public Ticket park(Car car) {
        for (ParkingLot lot : parkingLots) {
            if (!lot.isFull()) {
                return lot.park(car);
            }
        }
        throw new ParkingLotIsFullException("All parking lots are full");
    }

    public Car unpark(Ticket ticket) {
        for (ParkingLot lot : parkingLots) {
            try {
                return lot.unpark(ticket);
            } catch (CarNotFoundException e) {
            }
        }
        throw new CarNotFoundException("Parking lot not found for the given ticket");
    }
}