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
    public void testPolicemanNotifyFullAllParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(1);
        Policeman policemanSpy = spy(new Policeman());

        firstlot.registerNotifiable(policemanSpy);
        secondlot.registerNotifiable(policemanSpy);

        firstlot.park(new Car("AP-1234", Color.RED));
        secondlot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        assertThrows(ParkingLotIsFullException.class, () -> secondlot.park(new Car("AP-9998", Color.YELLOW)));

        verify(policemanSpy).notifyFull(firstlot.getParkingLotId());
        verify(policemanSpy).notifyFull(secondlot.getParkingLotId());
    }

    @Test
    public void testPolicemanNotifyFullSomeParkingLotsAreFull() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(2);
        Policeman policemanSpy = spy(new Policeman());

        firstlot.registerNotifiable(policemanSpy);
        secondlot.registerNotifiable(policemanSpy);

        firstlot.park(new Car("AP-1234", Color.RED));
        secondlot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        verify(policemanSpy).notifyFull(firstlot.getParkingLotId());

        verify(policemanSpy, Mockito.never()).notifyFull(secondlot.getParkingLotId());
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
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(1); // Two parking lots
        Policeman policemanSpy = spy(new Policeman());

        firstlot.registerNotifiable(policemanSpy);
        secondlot.registerNotifiable(policemanSpy);

        Ticket firstticket = firstlot.park(new Car("AP-1234", Color.RED));
        Ticket secondticket = secondlot.park(new Car("AP-5678", Color.BLUE));

        secondlot.unpark(secondticket);

        verify(policemanSpy).notifyAvailable(secondlot.getParkingLotId());

        assertDoesNotThrow(() -> secondlot.park(new Car("AP-9999", Color.GREEN)));
    }

    @Test
    public void testPolicemanNotifyFullSomeParkingLotsAreAvailable() throws Exception {
        Owner owner = new Owner();
        ParkingLot firstlot = owner.createParkingLot(1);
        ParkingLot secondlot = owner.createParkingLot(2);
        Policeman policemanSpy = spy(new Policeman());

        firstlot.registerNotifiable(policemanSpy);
        secondlot.registerNotifiable(policemanSpy);


        Ticket firstticket = firstlot.park(new Car("AP-1234", Color.RED));
        Ticket secondticket = secondlot.park(new Car("AP-5678", Color.BLUE));

        assertThrows(ParkingLotIsFullException.class, () -> firstlot.park(new Car("AP-9999", Color.GREEN)));
        verify(policemanSpy).notifyFull(firstlot.getParkingLotId());

        firstlot.unpark(firstticket);
        verify(policemanSpy).notifyAvailable(firstlot.getParkingLotId());

        assertDoesNotThrow(() -> firstlot.park(new Car("AP-8888", Color.BLACK)));
    }

}
