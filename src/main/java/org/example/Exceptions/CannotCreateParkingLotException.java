package org.example.Exceptions;

public class CannotCreateParkingLotException extends RuntimeException {
    public CannotCreateParkingLotException(String message) {
        super(message);
    }
}
