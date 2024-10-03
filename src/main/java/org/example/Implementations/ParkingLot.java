package org.example.Implementations;

import org.example.Enums.Color;
import org.example.Exceptions.*;
import org.example.Interfaces.Notifiable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ParkingLot {
    private final int totalSlots;
    private final List<Slot> slots;
    private final int parkingLotId;
    private final HashSet<Notifiable> notifiables = new HashSet<>();
    private Owner owner; // Added owner field

    public ParkingLot(int totalSlots, Owner owner) {
        if (totalSlots <= 0) {
            throw new IllegalArgumentException("Parking lot size must be positive.");
        }
        if (owner == null) {
            throw new CannotCreateParkingLotWithoutOwner("Parking lot cannot be created without an owner.");
        }
        this.totalSlots = totalSlots;
        this.owner = owner;
        this.parkingLotId = this.hashCode();
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
        throw new ParkingLotIsFullException("Parking lot is full.");
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new ParkingLotIsFullException("Parking lot is full.");
        }
        if (isCarAlreadyParked(car)) {
            throw new CarAlreadyParkedException("Car is already parked");
        }
        Slot nearestSlot = findNearestSlot();
        Ticket ticket = nearestSlot.park(car);
        if (isFull()) {
            notifyFull();
        }
        return ticket;
    }

    public Car unpark(Ticket ticket) throws InvalidTicketException {
        for (Slot slot : slots) {
            try {
                Car car = slot.unPark(ticket);
                if (!isFull()){
                     notifyAvailable();
                }
                return car;
            } catch (CarNotFoundException e) {
                // Continue searching in other slots
            }
        }
        throw new CarNotFoundException("Car not found in the parking lot.");
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

    public boolean isCarWithRegistrationNumberParked(String registrationNumber) throws CarNeedsRegistrationNumberException {
        return slots.stream()
                .anyMatch(slot -> slot.hasCarWithRegistrationNumber(registrationNumber));
    }

    public int countParkedCars() {
        int count = 0;
        for (Slot slot : slots) {
            if (!slot.isFree()) {
                count++;
            }
        }
        return count;
    }

    private void notifyFull() {
        for (Notifiable notifiable : notifiables ) {
            notifiable.notifyFull(this.parkingLotId);
        }
    }

    private void notifyAvailable() {
        for (Notifiable notifiable : notifiables) {
            notifiable.notifyAvailable(this.parkingLotId);
        }
    }

    public void registerNotifiable(Notifiable notifiable) {
        if (!notifiables.contains(notifiable)) {
            notifiables.add(notifiable);
        }
    }
    public int getParkingLotId(){
        return parkingLotId;
    }
}
