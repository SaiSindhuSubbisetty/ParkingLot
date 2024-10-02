package org.example.Implementations;

import org.example.Exceptions.*;
import org.example.Interfaces.Attendent;
import java.util.ArrayList;

public class SmartAttendent implements Attendent {
    private final ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private final ArrayList<Car> parkedCars = new ArrayList<>();

    @Override
    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        Attendent.super.assign(parkingLot, assignedParkingLots);
    }

    @Override
    public Ticket park(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car is already parked");
        }
        if (assignedParkingLots.isEmpty()) {
            throw new NoParkingLotAssignedException("No Parking Lot assigned.");
        }
        ParkingLot selectedLot = null;
        int minCars = Integer.MAX_VALUE;
        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isFull() && lot.countParkedCars() < minCars) {
                minCars = lot.countParkedCars();
                selectedLot = lot;
            }
        }
        if (selectedLot == null) {
            throw new ParkingLotIsFullException("All parking lots are full");
        }
        parkedCars.add(car);
        return selectedLot.park(car);
    }

    @Override
    public Car unpark(Ticket ticket) {
        return Attendent.super.unpark(ticket, assignedParkingLots, parkedCars);
    }
}