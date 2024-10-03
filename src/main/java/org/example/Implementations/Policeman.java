package org.example.Implementations;

import org.example.Interfaces.Notifiable;

public class Policeman implements Notifiable {

    public Policeman() {
        super();
    }

    @Override
    public void notifyFull(int parkingLotId) {
        System.out.println("Policeman notified: Parking lot with ID " + parkingLotId + " is full.");
    }

    @Override
    public void notifyAvailable(int parkingLotId) {
        System.out.println("Policeman notified: Parking lot with ID " + parkingLotId + " has available slots.");
    }
}
