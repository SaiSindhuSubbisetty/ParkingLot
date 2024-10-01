package org.example;

import org.example.Exceptions.ParkingLotAlreadyAssignmentException;

import java.util.ArrayList;

public class Owner extends Attendent implements Notifiable {
    private final ArrayList<ParkingLot> ownedParkingLots = new ArrayList<>();
    public void addOwnedParkingLot(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        super.assign(parkingLot);
    }

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        System.out.println("Owner notified: Parking lot " + parkingLot + " is full.");
    }

    @Override
    public void notifyAvailable(ParkingLot parkingLot) {
        System.out.println("Owner notified: Parking lot " + parkingLot + " has available slots.");
    }
}