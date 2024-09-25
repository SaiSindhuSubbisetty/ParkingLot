package org.example;

public class Ticket {
    private final int slotNumber;
    private final String parkingId;
    private final int ticketId;

    public Ticket(String parkingId, int slotNumber, int ticketId) {
        this.parkingId = parkingId;
        this.slotNumber = slotNumber;
        this.ticketId = ticketId;
    }

    public int getTicketId() {
        return ticketId;
    }

    public String getParkingId() {
        return parkingId;
    }

    public int getSlotNumber() {
        return slotNumber;
    }
}