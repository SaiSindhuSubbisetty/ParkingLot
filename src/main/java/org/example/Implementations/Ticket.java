package org.example.Implementations;

public class Ticket {
    final int slotNumber;
    private final int ticketId;

    public Ticket(int slotNumber, int ticketId) {
        this.slotNumber = slotNumber;
        this.ticketId = ticketId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }

    public int getTicketId() {
        return ticketId;
    }

}
