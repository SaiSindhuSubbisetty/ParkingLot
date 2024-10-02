package org.example.Exceptions;

public class SelectedLotIsFullException extends RuntimeException {
    public SelectedLotIsFullException(String message) {
        super(message);
    }
}
