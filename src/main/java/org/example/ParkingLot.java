package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.*;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLot {
    private final int totalSlots;
    private final List<Slot> slots;

    public ParkingLot(int totalSlots) {
        if (totalSlots <= 0) {
            throw new IllegalArgumentException("Parking lot size must be positive.");
        }
        this.totalSlots = totalSlots;
        this.slots = new ArrayList<>(totalSlots);
        for (int i = 0; i < totalSlots; i++) {
            slots.add(new Slot());
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
        if(isFull()){
            throw new ParkingLotIsFullException("Parking lot is full");
        }
        if (isCarAlreadyParked(car)) {
            throw new CarAlreadyParkedException("Car is already parked");
        }
        Slot nearestSlot = findNearestSlot();
        Ticket ticket = nearestSlot.park(car);
        return ticket;
    }

    public Car unpark(Ticket ticket) throws InvalidTicketException {
        for (Slot slot : slots) {
            try {
                return slot.unPark(ticket);
            } catch (CarNotFoundException e) {
                // Continue searching in other slots
            }
        }
        throw new CarNotFoundException("car not found in the parking lot");
    }

    public boolean isCarAlreadyParked(Car car) {
        for (Slot slot : slots) {
            if (slot.checkingCarInParkingSlot(car)) {
                return true;
            }
        }
        return false;
    }

    public boolean isFull() {
        for (Slot slot : slots) {
            if (slot.isFree()) {
                findNearestSlot();
                return false;
            }
        }
        return true;
    }

    public int countCarsByColor(Color color) {
        return (int) slots.stream()
                .filter(slot -> slot.hasCarOfColor(color))
                .count();
    }

    public boolean isCarWithRegistrationNumberParked(String registrationNumber) throws CarNeedsRegistrationNumberException{
        return slots.stream()
                .anyMatch(slot -> slot.hasCarWithRegistrationNumber(registrationNumber));
    }


}