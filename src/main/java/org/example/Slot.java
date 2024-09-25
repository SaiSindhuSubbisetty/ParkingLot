package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class Slot {
    private List<Car> cars;
    private int slotNumber;

    public Slot(int slotNumber) {
        this.slotNumber = slotNumber;
        this.cars = new ArrayList<>();
    }

    public boolean isFree() {
        return this.cars.isEmpty();
    }

    public List<Car> getCars() {
        return cars;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public String park(Car car) throws SlotIsOccupiedException {
        if (!this.isFree()) {
            throw new SlotIsOccupiedException("Slot is already occupied.");
        }
        this.cars.add(car);
        return "Ticket-" + UUID.randomUUID().toString();
    }

    public Car unPark(String ticket) throws CarNotFoundException {
        if (this.isFree()) {
            throw new CarNotFoundException("Car not found in the slot.");
        }
        Car parkedCar = this.cars.remove(0);
        return parkedCar;
    }
}