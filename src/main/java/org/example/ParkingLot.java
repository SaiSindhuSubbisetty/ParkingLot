package org.example;
import org.example.Exceptions.*;
import org.example.Enums.Color;
import org.example.Slot;
import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final int totalSlots;
    private final List<Slot> slots;
    private final List<Attendent> assistants = new ArrayList<>();
    private Owner owner;
    private Policeman policeman;
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
    public void addAssistant(Attendent assistant) {
        assistants.add(assistant);
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
        if (isFull()) {
            throw new ParkingLotIsFullException("Parking lot is full");
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
                if (!isFull()) {
                    notifyAvailable();
                }
                return car;
            } catch (CarNotFoundException e) {
                // Continue searching in other slots
            }
        }
        throw new CarNotFoundException("Car not found in the parking lot");
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

    public void addPoliceman(Policeman policeman) {
        this.policeman = policeman;
    }

    private void notifyFull() {
        if (owner != null) {
            owner.notifyFull(this);
        }
        if (policeman != null) {
            policeman.notifyFull(this);
        }
    }
    private void notifyAvailable() {
        if (owner != null) {
            owner.notifyAvailable(this);
        }
        if (policeman != null) {
            policeman.notifyAvailable(this);
        }
    }


}