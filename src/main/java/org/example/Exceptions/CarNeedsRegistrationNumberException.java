package org.example.Exceptions;

public class CarNeedsRegistrationNumberException extends RuntimeException {
    public CarNeedsRegistrationNumberException(String message) {
        super(message);
    }
}
