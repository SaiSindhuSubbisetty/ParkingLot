package org.example;

public class ParkingLotIsFullException extends RuntimeException {
    public ParkingLotIsFullException(String message) {
        super(message);
    }
}
