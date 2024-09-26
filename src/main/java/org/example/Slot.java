package org.example;

import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.InvalidTicketException;
import org.example.Exceptions.SlotIsOccupiedException;

public class Slot {
    public Car car;
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
        if(this.ticket.equals(ticket)) {
            Car car = this.car;
            this.car = null;
            this.ticket = null;
            return car;
        }
        throw new InvalidTicketException("Invalid ticket.");

    }

}