package org.example.Exceptions;

public class CannotCreateParkingLotWithoutOwner extends RuntimeException {
    public CannotCreateParkingLotWithoutOwner(String message) {
        super(message);
    }
}
