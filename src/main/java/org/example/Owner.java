package org.example;

import org.example.Attendent;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.ParkingLot;

import java.util.ArrayList;

public class Owner extends Attendent {
    private final ArrayList<ParkingLot> ownedParkingLots = new ArrayList<>();
    public void addOwnedParkingLot(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        ownedParkingLots.add(parkingLot);
        assign(parkingLot);
    }
    public void notifyFull(ParkingLot parkingLot) {
        System.out.println("Owner notified: Parking lot " + parkingLot + " is full.");
    }
    public void notifyAvailable(ParkingLot parkingLot) {
        System.out.println("Owner notified: Parking lot " + parkingLot + " has available slots.");
    }
}