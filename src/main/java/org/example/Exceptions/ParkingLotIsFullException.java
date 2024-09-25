package org.example.Exceptions;

public class ParkingLotIsFullException extends RuntimeException {
    public ParkingLotIsFullException(String message) {
        super(message);
    }
}
