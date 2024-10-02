package org.example.Interfaces;

import org.example.Implementations.ParkingLot;

public interface Notifiable {
    void notifyFull(ParkingLot parkingLot);
    void notifyAvailable(ParkingLot parkingLot);
}
