package org.example;

public interface Notifiable {
    void notifyFull(ParkingLot parkingLot);
    void notifyAvailable(ParkingLot parkingLot);
}
