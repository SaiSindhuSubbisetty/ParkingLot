package org.example.Interfaces;

import org.example.Exceptions.*;
import org.example.Implementations.Car;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.Ticket;

import java.util.ArrayList;

public interface Attendable {
    void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException;

    Ticket park(Car car) throws ParkingLotIsFullException, CarAlreadyParkedException;

    Car unpark(Ticket ticket) throws CarNotFoundException;

    void checkIfCarIsAlreadyParked(Car car) throws CarAlreadyParkedException;

}