package org.example;
import org.example.Exceptions.*;
import org.example.Enums.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
public class Attendent {
    private final ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private final ArrayList<Car> parkedCars = new ArrayList<>();
    public void assign(ParkingLot parkingLot) {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }
    public Ticket park(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car already assigned to this parking lot");
        }
        ParkingLot selectedLot = null;
        int minCars = Integer.MAX_VALUE;
        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isFull() && lot.countParkedCars() < minCars) {
                minCars = lot.countParkedCars();
                selectedLot = lot;
            }
        }
        if (selectedLot != null) {
            parkedCars.add(car);
            return selectedLot.park(car);
        }
        throw new ParkingLotIsFullException("All parking lots are full");
    }
    public Car unpark(Ticket ticket) {
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
