package org.example;

public class SlotIsOccupiedException extends RuntimeException {
    public SlotIsOccupiedException(String message) {
        super(message);
    }
}
