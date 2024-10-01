package org.example;

public class Policeman implements Notifiable {

    @Override
    public void notifyFull(ParkingLot parkingLot) {
        System.out.println("Policeman notified: Parking lot " + parkingLot + " is full.");
    }

    @Override
    public void notifyAvailable(ParkingLot parkingLot) {
        System.out.println("Policeman notified: Parking lot " + parkingLot + " has available slots.");
    }
}