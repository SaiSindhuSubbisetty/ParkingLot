package org.example.Implementations;

import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.SlotIsOccupiedException;

public class Slot {
    public Car car;
    public final int slotNumber;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.car = null;
    }

    public boolean isFree() {
        return this.car == null;
    }

    public void park(Car car) throws SlotIsOccupiedException {
        if (!this.isFree()) {
            throw new SlotIsOccupiedException("Slot is already occupied.");
        }
        this.car = car;
    }

    public Car unPark() throws CarNotFoundException {
        if (this.isFree()) {
            throw new CarNotFoundException("Car not found in the slot.");
        }
        Car parkedCar = this.car;
        this.car = null;
        return parkedCar;
    }

}