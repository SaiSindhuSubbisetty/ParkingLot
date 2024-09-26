package org.example;

import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.CarNotFoundException;
import org.example.Exceptions.ParkingLotAlreadyAssignmentException;
import org.example.Exceptions.ParkingLotIsFullException;

import java.util.ArrayList;

public class Attendent {
    private final ArrayList<ParkingLot> assignedparkingLots = new ArrayList<>();
    private ArrayList<Car> parkedCars = new ArrayList<>();

    public void assign(ParkingLot parkingLot) {
        if (assignedparkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        assignedparkingLots.add(parkingLot);
    }

    public Ticket park(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car already assigned to this parking lot");
        }
        for (ParkingLot lot : assignedparkingLots) {
            if (!lot.isFull()) {
                parkedCars.add(car);
                return lot.park(car);
            }
        }
        throw new ParkingLotIsFullException("All parking lots are full");
    }

    public Car unpark(Ticket ticket){
        for (ParkingLot lot : assignedparkingLots) {
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