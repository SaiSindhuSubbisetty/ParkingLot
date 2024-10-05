package org.example;

import org.example.Enums.Color;
import org.example.Exceptions.ParkingLotIsFullException;
import org.example.Implementations.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PolicemanTest {

    @Test
    public void testPolicemanNotifiedWhenParkingLotIsFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Policeman policeman = new Policeman();
        owner.registerNotifiable(parkingLot, policeman);

        Car car = new Car("AP-1234", Color.RED);
        parkingLot.park(car);

        assertThrows(ParkingLotIsFullException.class, () -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testRegisterNotifiableToPolicemen() throws Exception {
        Owner owner = new Owner();
        Policeman policeman = spy(new Policeman());
        ParkingLot parkingLot = owner.createParkingLot(1);
        Attendent attendent = new Attendent();

        owner.assignParkingLotToAttendent(attendent, parkingLot);
        owner.registerNotifiable(parkingLot, policeman);

        Car car = new Car("AP-1234", Color.RED);
        attendent.park(car);

        verify(policeman, times(1)).notifyFull(parkingLot.getParkingLotId());
    }

    @Test
    public void testPolicemanNotifyFullAllParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(1);
        ParkingLot secondLot = owner.createParkingLot(1);
        Policeman policemanSpy = spy(new Policeman());

        owner.registerNotifiable(firstLot,policemanSpy);
        owner.registerNotifiable(secondLot,policemanSpy);

        firstLot.park(new Car("AP-1234", Color.RED));
        secondLot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstLot.park(new Car("AP-9999", Color.GREEN)));
        assertThrows(ParkingLotIsFullException.class, () -> secondLot.park(new Car("AP-1432", Color.YELLOW)));

        verify(policemanSpy, times(1)).notifyFull(firstLot.getParkingLotId());
        verify(policemanSpy, times(1)).notifyFull(secondLot.getParkingLotId());
    }

    @Test
    public void testPolicemanNotifyFullSomeParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(1);
        ParkingLot secondLot = owner.createParkingLot(2);
        Policeman policemanSpy = spy(new Policeman());

        owner.registerNotifiable(firstLot,policemanSpy);
        owner.registerNotifiable(secondLot,policemanSpy);

        firstLot.park(new Car("AP-1234", Color.RED));
        secondLot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstLot.park(new Car("AP-9999", Color.GREEN)));

        verify(policemanSpy, times(1)).notifyFull(firstLot.getParkingLotId());
        verify(policemanSpy, never()).notifyFull(secondLot.getParkingLotId());
    }

    @Test
    public void testPolicemanNotifiedWhenParkingLotAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot parkingLot = owner.createParkingLot(1);
        Policeman policeman = new Policeman();

        owner.registerNotifiable(parkingLot,policeman);

        Car car = new Car("AP-1234", Color.RED);
        Ticket ticket = parkingLot.park(car);

        parkingLot.unpark(ticket);

        assertDoesNotThrow(() -> parkingLot.park(new Car("AP-5678", Color.BLUE)));
    }

    @Test
    public void testPolicemanNotifyAvailableSecondParkingLotIsAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(1);
        ParkingLot secondLot = owner.createParkingLot(1);
        Policeman policemanSpy = spy(new Policeman());

        owner.registerNotifiable(firstLot,policemanSpy);
        owner.registerNotifiable(secondLot,policemanSpy);

        firstLot.park(new Car("AP-1234", Color.RED));
        Ticket secondTicket = secondLot.park(new Car("AP-5678", Color.BLUE));

        secondLot.unpark(secondTicket);

        verify(policemanSpy, times(1)).notifyAvailable(secondLot.getParkingLotId());

        assertDoesNotThrow(() -> secondLot.park(new Car("AP-9999", Color.GREEN)));
    }

    @Test
    public void testPolicemanNotifyFullSomeParkingLotsAreAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstLot = owner.createParkingLot(1);
        ParkingLot secondLot = owner.createParkingLot(2);
        Policeman policemanSpy = spy(new Policeman());

        owner.registerNotifiable(firstLot,policemanSpy);
        owner.registerNotifiable(secondLot,policemanSpy);

        Ticket firstTicket = firstLot.park(new Car("AP-1234", Color.RED));
        secondLot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstLot.park(new Car("AP-9999", Color.GREEN)));
        verify(policemanSpy, times(1)).notifyFull(firstLot.getParkingLotId());

        firstLot.unpark(firstTicket);
        verify(policemanSpy, times(1)).notifyAvailable(firstLot.getParkingLotId());

        assertDoesNotThrow(() -> firstLot.park(new Car("AP-8888", Color.BLACK)));
    }
}
