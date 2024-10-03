package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

class PolicemanTest {

    @Test
    public void testPolicemanNotifiedWhenParkingLotFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Policeman policeman = new Policeman();

        parkingLot.registerNotifiable(policeman);

        Car car = new Car("AP-1234", Color.RED);
        parkingLot.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testNotifyFullAllParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot1 = owner.createParkingLot(1);
        ParkingLot lot2 = owner.createParkingLot(1);
        Policeman policemanSpy = spy(new Policeman());

        lot1.registerNotifiable(policemanSpy);
        lot2.registerNotifiable(policemanSpy);

        lot1.park(new Car("AP-1234", Color.RED));
        lot2.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> lot1.park(new Car("AP-9999", Color.GREEN)));
        assertThrows(ParkingLotIsFullException.class, () -> lot2.park(new Car("AP-9998", Color.YELLOW)));

        verify(policemanSpy).notifyFull(lot1.getParkingLotId());
        verify(policemanSpy).notifyFull(lot2.getParkingLotId());
    }

    @Test
    public void testNotifyFullSomeParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot1 = owner.createParkingLot(1);
        ParkingLot lot2 = owner.createParkingLot(2); // This lot has 2 spaces
        Policeman policemanSpy = spy(new Policeman());

        lot1.registerNotifiable(policemanSpy);
        lot2.registerNotifiable(policemanSpy);

        lot1.park(new Car("AP-1234", Color.RED)); // lot1 is full
        lot2.park(new Car("AP-5678", Color.BLUE)); // lot2 still has space

        assertThrows(ParkingLotIsFullException.class, () -> lot1.park(new Car("AP-9999", Color.GREEN)));
        verify(policemanSpy).notifyFull(lot1.getParkingLotId());

        verify(policemanSpy, Mockito.never()).notifyFull(lot2.getParkingLotId());
    }

    @Test
    public void testPolicemanNotifiedWhenParkingLotAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Policeman policeman = new Policeman();

        parkingLot.registerNotifiable(policeman);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        parkingLot.unpark(ticket);

        assertDoesNotThrow(() -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testPolicemanNotifyAvailableSecondParkingLotIsAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot lot1 = owner.createParkingLot(1);
        ParkingLot lot2 = owner.createParkingLot(1); // Two parking lots
        Policeman policemanSpy = spy(new Policeman());

        lot1.registerNotifiable(policemanSpy);
        lot2.registerNotifiable(policemanSpy);

        Ticket ticket1 = lot1.park(new Car("AP-1234", Color.RED));
        Ticket ticket2 = lot2.park(new Car("AP-5678", Color.BLUE));

        lot2.unpark(ticket2);

        verify(policemanSpy).notifyAvailable(lot2.getParkingLotId());

        assertDoesNotThrow(() -> lot2.park(new Car("AP-9999", Color.GREEN)));
    }


}
