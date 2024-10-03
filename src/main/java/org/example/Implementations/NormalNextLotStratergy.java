package org.example.Implementations;

import org.example.Exceptions.ParkingLotIsFullException;

import java.util.ArrayList;

public class NormalNextLotStratergy extends NextLotStratergy {

    public ParkingLot getNextLot(ArrayList<ParkingLot> assignedParkingLots) {
        ParkingLot selectedLot = null;

        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isFull()) {
                selectedLot = lot;
            }
        }
        if(selectedLot == null) {
            throw new ParkingLotIsFullException("All parking lots are full");
        }
        return selectedLot;
    }

}
