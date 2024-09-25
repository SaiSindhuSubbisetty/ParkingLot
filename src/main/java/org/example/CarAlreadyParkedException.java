package org.example;

public class CarAlreadyParkedException extends RuntimeException {
    public CarAlreadyParkedException(String message) {
        super(message);
    }
}
