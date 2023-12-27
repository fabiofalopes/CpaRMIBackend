//JUnit test class for the Stuff class

import Reservation.ReservaUtility;
import Server.RentalsServerImpl;
import org.junit.Test;

import java.rmi.RemoteException;
import java.time.LocalDateTime;

public class TestStuff {

    @Test
    // Test ReservaUtility
    public void testReservaUtility() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        ru.listarReservas();
    }

    @Test
    public void testPrintTable() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        String a = ru.toStringTable();
        System.out.println(a);
    }

    @Test
    public void testRentalsReserva() throws RemoteException {
        RentalsServerImpl rentalsServerImpl = new RentalsServerImpl();
        System.out.println(LocalDateTime.now());
        rentalsServerImpl.reservaSombrinha(LocalDateTime.of(
                        2024, 12, 31, 9, 0),
                        "A",
                        1);
    }
    //Construtor da reverva utility


}
