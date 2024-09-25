package org.example;

import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.SlotIsOccupiedException;

class Slot {
    private Car car;
    private final int slotNumber;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.car = null;
    }

    public boolean isFree() {
        return this.car == null;
    }

    public Car getCar() {
        return car;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String park(Car car) throws SlotIsOccupiedException {
        if (!this.isFree()) {
            throw new SlotIsOccupiedException("Slot is already occupied.");
        }
        this.car = car;
        return null;
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