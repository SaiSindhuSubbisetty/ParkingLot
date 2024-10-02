package org.example;

import org.example.Exceptions.CarAlreadyParkedException;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Exceptions.SelectedLotIsFullException;
import java.util.ArrayList;

public class SmartAttendent extends Attendent {
    private final ArrayList<ParkingLot> assignedParkingLots = new ArrayList<>();
    private final ArrayList<Car> parkedCars = new ArrayList<>();

    @Override
    public Ticket park(Car car) {
        if (parkedCars.contains(car)) {
            throw new CarAlreadyParkedException("Car is already parked");
        }
        if (assignedParkingLots.isEmpty()) {
            throw new ParkingLotIsFullException.NoParkingLotAssignedException("No Parking Lot assigned.");
        }

        ParkingLot selectedLot = null;
        int minCars = Integer.MAX_VALUE;
        for (ParkingLot lot : assignedParkingLots) {
            if (!lot.isFull() && lot.countParkedCars() < minCars) {
                minCars = lot.countParkedCars();
                selectedLot = lot;
            }
        }
        if (selectedLot == null) {
            throw new SelectedLotIsFullException("Selected Lot is full.");
        }
        parkedCars.add(car);
        return selectedLot.park(car);
    }

    public void assign(ParkingLot parkingLot) {
        assignedParkingLots.add(parkingLot);  // Ensure parking lots can be assigned
    }

}
