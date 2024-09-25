package org.example;

public class Ticket {
    private final int slotNumber;
    private final int ticketId;

    public Ticket(int slotNumber, int ticketId) {
        this.slotNumber = slotNumber;
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}
