package org.example;

import org.example.Enums.OwnerType;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import java.util.ArrayList;

public class Owner extends Attendent implements Notifiable {
    private OwnerType ownerType;
    private Attendent assignedAttendent;
    private final ArrayList<ParkingLot> ownedParkingLots = new ArrayList<>();

    public void addOwnedParkingLot(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        ownedParkingLots.add(parkingLot);  // Add to the owner's list
        assignedAttendent.assign(parkingLot);  // Assign to the attendant
    }

    public Owner(OwnerType ownerType) {
        this.ownerType = ownerType;
        this.assignedAttendent = ownerType.getAttendant(); // Get the attendant based on owner type
    }

    @Override
    public Ticket park(Car car) {
        return assignedAttendent.park(car);
    }

    @Override
    public Car unpark(Ticket ticket) {
        return assignedAttendent.unpark(ticket);
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
