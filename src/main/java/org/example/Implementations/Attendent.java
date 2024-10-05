package org.example.Implementations;
import org.example.Exceptions.*;
import org.example.Interfaces.Attendable;

import java.util.ArrayList;
public class Attendent implements Attendable {

    private final NextLotStratergy nextLotStrategy;
    protected final ArrayList<ParkingLot> assignedParkingLots;
    private final ArrayList<Car> parkedCars = new ArrayList<>();

    public Attendent(NextLotStratergy strategy) {
        this.assignedParkingLots = new ArrayList<>();
        this.nextLotStrategy = strategy;
    }

    public Attendent(){
        this.assignedParkingLots = new ArrayList<>();
        this.nextLotStrategy = new NormalNextLotStratergy();
    }
    @Override
    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }

    @Override
    public void checkIfCarIsAlreadyParked(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car already assigned to this parking lot");
        }
    }

    @Override
    public Ticket park(Car car) {
        if (assignedParkingLots.isEmpty()) {
            throw new NoParkingLotAssignedException("No Parking Lot assigned.");
        }
        checkIfCarIsAlreadyParked(car);

        ParkingLot selectedLot = nextLotStrategy.getNextLot(assignedParkingLots);

        Ticket ticket = selectedLot.park(car);
        parkedCars.add(car);
        return ticket;
    }

    @Override
    public Car unpark(Ticket ticket) {
        for (ParkingLot lot : assignedParkingLots) {
            try {
                Car unparkedCar = lot.unpark(ticket);
                parkedCars.remove(unparkedCar);
                return unparkedCar;
            } catch (InvalidTicketException e) {
                // Continue searching in other parking lots
            }
        }
        throw new InvalidTicketException("Invalid ticket.");
    }


}
