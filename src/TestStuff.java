//JUnit test class for the Stuff class

import Reservation.ReservaUtility;
import Server.RentalsServerImpl;
import org.junit.Assert;
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

    // Insere multiplas reservas
    @Test
    public void testInsereMultiplasReservas() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 8, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 9, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 10, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 11, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 12, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 13, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 14, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 15, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 16, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 17, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 18, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 19, 0), "A", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 20, 0), "A", 1);
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 8, 0), "A", 1);
    }

    // Tenta inserir com data inválida - Data anterior à atual
    @Test(expected = IllegalArgumentException.class)
    public void testReservaSombrinha() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2023, 1, 1, 8, 0), "A", 1);
        System.out.println(ru.toStringTable());
    }

    // Tenta inserir com data hora inválida - antes das 8:00
    @Test(expected = IllegalArgumentException.class)
    public void testReservaSombrinha2() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 7, 0), "A", 1);
        System.out.println(ru.toStringTable());
    }

    // Tenta criar reserva com id praia inválido
    @Test //(expected = IllegalArgumentException.class)
    public void testReservaSombrinha3() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2024, 1, 1, 8, 0),
                "Z",
                1
        );
        System.out.println(ru.toStringTable());
    }

    // Testa cancelar reserva
    @Test
    public void testCancelarReserva() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.cancelarReserva(LocalDateTime.of(2024, 11, 23, 15, 0), "B", 1);
        System.out.println(ru.toStringTable());
    }

    // Testa cancelar reserva e insere mais 3 reservas
    @Test
    public void testCancelarReserva2() {
        ReservaUtility ru = new ReservaUtility();
        ru.loadFromFile("input-files/persistence.txt");
        System.out.println(ru.toStringTable());
        ru.cancelarReserva(LocalDateTime.of(2024, 11, 23, 15, 0), "B", 1);
        ru.cancelarReserva(LocalDateTime.of(2024, 4, 2, 8, 0), "C", 1);
        ru.cancelarReserva(LocalDateTime.of(2024, 4, 2, 9, 0), "A", 3);
        ru.cancelarReserva(LocalDateTime.of(2024, 4, 2, 10, 0), "A", 3);
        System.out.println(ru.toStringTable());
        ru.inserirReserva(LocalDateTime.of(2024, 11, 23, 15, 0), "B", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 11, 23, 16, 0), "B", 1);
        ru.inserirReserva(LocalDateTime.of(2024, 11, 23, 17, 0), "B", 1);
        System.out.println(ru.toStringTable());
    }
}
