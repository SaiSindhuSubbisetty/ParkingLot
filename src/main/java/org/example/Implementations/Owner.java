package org.example.Implementations;

import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.Exceptions.notOwnedParkingLotException;
import org.example.Interfaces.Notifiable;

import java.util.ArrayList;
import java.util.List;

public class Owner extends Attendent implements Notifiable {
    private List<ParkingLot> ownerParkingLots = new ArrayList<>();

    public Owner() {
        super();
    }

    public ParkingLot createParkingLot(int totalSlots) throws Exception {
        ParkingLot parkingLot = new ParkingLot(totalSlots, this);
        parkingLot.registerNotifiable(this);
        this.ownerParkingLots.add(parkingLot);
        return parkingLot;
    }

    public void assignParkingLotToAttendent(Attendent attendent, ParkingLot parkingLot) {
        if (!ownerParkingLots.contains(parkingLot)) {
            throw new notOwnedParkingLotException("This ParkingLot is not owned by the Owner");
        }
        if (attendent.assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        attendent.assignedParkingLots.add(parkingLot);
    }

    public void assignParkingLotToSelf(ParkingLot parkingLot) {
        if (!ownerParkingLots.contains(parkingLot)) {
            throw new notOwnedParkingLotException("This ParkingLot is not owned by this Owner");
        }
        super.assign(parkingLot);
    }

    public void registerNotifiable(ParkingLot parkingLot, Notifiable notifiable) {
        parkingLot.registerNotifiable(notifiable);
    }

    public void notifyFull(int parkingLotId) {
        System.out.println("Owner notified: Parking lot with ID " + parkingLotId + " is full.");
    }

    public void notifyAvailable(int parkingLotId) {
        System.out.println("Owner notified: Parking lot with ID " + parkingLotId + " has available slots.");
    }
}
