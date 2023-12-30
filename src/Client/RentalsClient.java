package Client;

import Server.RentalsServerIntf;

import java.rmi.*;
import java.time.LocalDateTime;

public class RentalsClient {
    public static void main(String args[]) {

        final String REMOTE_SERVER = "/RentalsServer";

        try {
            //args[0] = "127.0.0.1";
            String rentalsServerURL = "rmi://" + args[0] + REMOTE_SERVER;

            // Declaration of the remote object
            RentalsServerIntf rentalsServerIntf = (RentalsServerIntf) Naming.lookup(rentalsServerURL);

            System.out.println(rentalsServerIntf.testRMI());

            System.out.println(rentalsServerIntf.listAllMethods());

            // Print table de reservas que vem em String
            System.out.println(rentalsServerIntf.getReservasTable());

            System.out.println("Se -1 é porque não foi possível reservar");
            int id1 = rentalsServerIntf.reservaSombrinha(18, "2024-12-12", "A", 1);
            System.out.println("nova reserva id1: " + id1);
            int id2 = rentalsServerIntf.reservaSombrinha(18, "2024-12-12", "A", 2);
            System.out.println("nova reserva id2: " + id2);
            int id3 = rentalsServerIntf.reservaSombrinha(18, "2024-12-12", "A", 3);
            System.out.println("nova reserva id3: " + id3);

            // Print table de reservas que vem em String
            System.out.println(rentalsServerIntf.getReservasTable());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
