package org.example.Implementations;
import org.example.Exceptions.*;
import org.example.Interfaces.Attendable;
import java.util.ArrayList;
public class Attendent<T extends NextLotStratergy> implements Attendable {
    private final ArrayList<ParkingLot> assignedParkingLots;
    private final ArrayList<Car> parkedCars = new ArrayList<>();
    private final T nextLotStrategy;

    public Attendent(T strategy) {
        this.assignedParkingLots = new ArrayList<>();
        this.nextLotStrategy = strategy;
    }

    public Attendent(){
        this.assignedParkingLots = new ArrayList<>();
        this.nextLotStrategy = (T) new NormalNextLotStratergy();
    }

    @Override
    public void assign(ParkingLot parkingLot) throws ParkingLotAlreadyAssignmentException {
        if (assignedParkingLots.contains(parkingLot)) {
            throw new ParkingLotAlreadyAssignmentException("Parking lot already assigned.");
        }
        assignedParkingLots.add(parkingLot);
    }
    @Override
    public Ticket park(Car car) {
        if (assignedParkingLots.isEmpty()) {
            throw new NoParkingLotAssignedException("No Parking Lot assigned.");
        }
        checkIfCarIsAlreadyParked(car);

        ParkingLot selectedLot = nextLotStrategy.getNextLot(assignedParkingLots);

        Ticket ticket = selectedLot.park(car);
        return ticket;
    }

    @Override
    public void checkIfCarIsAlreadyParked(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car already assigned to this parking lot");
        }
    }

    @Override
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
