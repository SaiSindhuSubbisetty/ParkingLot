package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private final int totalSlots;
    private final List<Slot> slots;
    private final String parkingId;
    private final AtomicInteger ticketCounter = new AtomicInteger(0);

    public ParkingLot(int totalSlots, String parkingId) {
        if (totalSlots <= 0) {
            throw new IllegalArgumentException("Parking lot size must be positive.");
        }
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>(totalSlots);
        this.parkingId = parkingId;
        for (int i = 0; i < totalSlots; i++) {
            slots.add(new Slot(i));
        }
    }

    public String getParkingId() {
        return parkingId;
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
        return new Ticket(parkingId, nearestSlot.getSlotNumber(), ticketCounter.incrementAndGet());
    }

    public Car unpark(Ticket ticket) {
        int slotNumber = ticket.getSlotNumber();
        if (slotNumber >= 0 && slotNumber < totalSlots) {
            Slot slot = slots.get(slotNumber);
            if (!slot.isFree()) {
                return slot.unPark(ticket.getParkingId());
            }
        }
        throw new CarNotFoundException("Invalid ticket or car not found in the parking lot");
    }

    public int countCarsByColor(Color color) {
        int count = 0;
        for (Slot slot : slots) {
            for (Car car : slot.getCars()) {
                if (car.getColor() == color) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean isCarParked(Car car) {
        for (Slot slot : slots) {
            if (slot.getCars().contains(car)) {
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
}