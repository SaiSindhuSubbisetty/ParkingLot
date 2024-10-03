package org.example.Implementations;

import java.util.ArrayList;

public abstract class NextLotStratergy {
    abstract ParkingLot getNextLot(ArrayList<ParkingLot> assignedParkingLots);
}
