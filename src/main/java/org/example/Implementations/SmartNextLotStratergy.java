package org.example.Implementations;

import org.example.Exceptions.ParkingLotIsFullException;

import java.util.ArrayList;

public class SmartNextLotStratergy extends NextLotStratergy {
    public ParkingLot getNextLot(ArrayList<ParkingLot> assignedParkingLots) {
        ParkingLot selectedLot = null;
        int minCars = Integer.MAX_VALUE;
        for (ParkingLot parkinglot : assignedParkingLots) {
            if (!parkinglot.isFull() && parkinglot.countParkedCars() < minCars) {
                minCars = parkinglot.countParkedCars();
                selectedLot = parkinglot;
            }
        }
        if (selectedLot == null || selectedLot.isFull()) {
            throw new ParkingLotIsFullException("All parking lots are full");
        }
        return selectedLot;
    }
}
