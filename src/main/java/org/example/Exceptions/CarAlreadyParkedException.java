package org.example.Exceptions;

public class CarAlreadyParkedException extends RuntimeException {
    public CarAlreadyParkedException(String message) {
        super(message);
    }
}
