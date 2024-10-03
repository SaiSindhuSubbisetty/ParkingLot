package org.example.Implementations;

import org.example.Exceptions.ParkingLotIsFullException;

import java.util.ArrayList;

public class SmartNextLotStratergy extends NextLotStratergy {
    public ParkingLot getNextLot(ArrayList<ParkingLot> assignedParkingLots) {
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
        return selectedLot;
    }
}
