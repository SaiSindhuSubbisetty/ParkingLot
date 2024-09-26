package org.example.Implementations;

import org.example.Enums.Color;
import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotIsFullException;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private final int totalSlots;
    private final List<Slot> slots;
    private final AtomicInteger ticketCounter = new AtomicInteger(0);

    public ParkingLot(int totalSlots) {
        if (totalSlots <= 0) {
            throw new IllegalArgumentException("Parking lot size must be positive.");
        }
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>(totalSlots);
        for (int i = 0; i < totalSlots; i++) {
            slots.add(new Slot(i));
        }
    }

    private Slot findNearestSlot() {
        for (Slot slot : slots) {
            if (slot.isFree()) {
                return slot;
            }
        }
        throw new ParkingLotIsFullException("Parking lot is full");
    }

    public Ticket park(Car car) {
        if (isCarParked(car)) {
            throw new CarAlreadyParkedException("Car is already parked");
        }
        Slot nearestSlot = findNearestSlot();
        nearestSlot.park(car);
        return new Ticket(nearestSlot.slotNumber, ticketCounter.incrementAndGet());
    }

    public Car unpark(Ticket ticket) {
        int slotNumber = ticket.slotNumber;
        if (slotNumber >= 0 && slotNumber < totalSlots) {
            Slot slot = slots.get(slotNumber);
            if (!slot.isFree()) {
                return slot.unPark();
            }
        }
        throw new CarNotFoundException("Invalid ticket or car not found in the parking lot");
    }

    public boolean isCarParked(Car car) {
        for (Slot slot : slots) {
            if (slot.car != null && slot.car.equals(car)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (Slot slot : slots) {
            if (slot.isFree()) {
                return false;
            }
        }
        return true;
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (Slot slot : slots) {
            Car car = slot.car;
            if (car != null && car.color == color) {
                count++;
            }
        }
        return count;
    }


}
