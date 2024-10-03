package org.example.Exceptions;

public class notOwnedParkingLotException extends RuntimeException {
    public notOwnedParkingLotException(String message) {
        super(message);
    }
}
