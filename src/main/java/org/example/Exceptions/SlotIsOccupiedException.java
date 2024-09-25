package org.example.Exceptions;

public class SlotIsOccupiedException extends RuntimeException {
    public SlotIsOccupiedException(String message) {
        super(message);
    }
}
