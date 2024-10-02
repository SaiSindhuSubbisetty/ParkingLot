package org.example.Interfaces;

import org.example.Exceptions.*;
import org.example.Implementations.Car;
import org.example.Implementations.ParkingLot;
import org.example.Implementations.Ticket;

import java.util.ArrayList;

public interface Attendent {
    void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException;

    Ticket park(Car car) throws ParkingLotIsFullException, CarAlreadyParkedException;

    Car unpark(Ticket ticket) throws CarNotFoundException;

    default void assign(ParkingLot parkingLot, ArrayList<ParkingLot> assignedParkingLots) throws ParkingLotAlreadyAssignmentException {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }

    default Car unpark(Ticket ticket, ArrayList<ParkingLot> assignedParkingLots, ArrayList<Car> parkedCars) throws CarNotFoundException {
        for (ParkingLot lot : assignedParkingLots) {
            try {
                Car unparkedCar = lot.unpark(ticket);
                parkedCars.remove(unparkedCar);
                return unparkedCar;
            } catch (CarNotFoundException e) {
                // Continue searching in other parking lots
            }
        }
        throw new CarNotFoundException("Car not found in assigned parking lot");
    }
}