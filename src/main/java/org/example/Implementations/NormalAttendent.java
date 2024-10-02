package org.example.Implementations;
import org.example.Exceptions.*;
import org.example.Interfaces.Attendent;
import java.util.ArrayList;
public class NormalAttendent implements Attendent {
    private final ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private final ArrayList<Car> parkedCars = new ArrayList<>();
    @Override
    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        Attendent.super.assign(parkingLot, assignedParkingLots);
    }
    @Override
    public Ticket park(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car already assigned to this parking lot");
        }
        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isFull()) {
                parkedCars.add(car);
                return lot.park(car);
            }
        }
        throw new ParkingLotIsFullException("All parking lots are full");
    }
    @Override
    public Car unpark(Ticket ticket) {
        return Attendent.super.unpark(ticket, assignedParkingLots, parkedCars);
    }
}
