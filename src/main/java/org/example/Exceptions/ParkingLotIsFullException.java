package org.example.Exceptions;

public class ParkingLotIsFullException extends RuntimeException {
    public ParkingLotIsFullException(String message) {
        super(message);
    }

    public static class NoParkingLotAssignedException extends RuntimeException {
      public NoParkingLotAssignedException(String message) {
        super(message);
      }
    }
}
