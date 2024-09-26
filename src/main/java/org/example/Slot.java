package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.CarNeedsRegistrationNumberException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.SlotIsOccupiedException;

public class Slot {
    private Car car;
    private Ticket ticket;

    public Slot() {
        this.car = null;
        this.ticket = null;
    }

    public boolean isFree() {
        return this.car == null;
    }

    public Ticket park(Car car) throws SlotIsOccupiedException {
        if (!this.isFree()) {
            throw new SlotIsOccupiedException("Slot is already occupied.");
        }
        this.car = car;
        this.ticket = new Ticket();
        return this.ticket;
    }

    public Car unPark(Ticket ticket) throws CarNotFoundException {
        if (this.isFree()) {
            throw new CarNotFoundException("Car not found in the slot.");
        }
        if (this.ticket.equals(ticket)) {
            Car car = this.car;
            this.car = null;
            this.ticket = null;
            return car;
        }
        throw new InvalidTicketException("Invalid ticket.");

    }

    public boolean hasCarOfColor(Color color) {
        return !isFree() && car.isColor(color);
    }

    public boolean hasCarWithRegistrationNumber(String registrationNumber) throws CarNeedsRegistrationNumberException {
        if (!isFree() && car.hasRegistrationNumber(registrationNumber)) {
            return true;
        }
        throw new CarNeedsRegistrationNumberException("Car needs registration number.");
    }

    public boolean checkingCarInParkingSlot(Car car) {
        return !isFree() && this.car.equals(car);
    }

}