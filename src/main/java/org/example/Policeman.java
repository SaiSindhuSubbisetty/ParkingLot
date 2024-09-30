package org.example;

public class Policeman {
    public void notifyFull(ParkingLot parkingLot) {
        System.out.println("Policeman notified: Parking lot " + parkingLot + " is full.");
    }
    public void notifyAvailable(ParkingLot parkingLot) {
        System.out.println("Policeman notified: Parking lot " + parkingLot + " has available slots.");
    }
}