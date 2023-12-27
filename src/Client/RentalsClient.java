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

            if (rentalsServerIntf.reservaSombrinha(
                    LocalDateTime.of(2024, 1, 1, 8, 0),
                    "A",
                    1))
            {
                System.out.println("Reserva efetuada com sucesso!");
                System.out.println(); // Blank line
            } else {
                System.out.println("Reserva não efetuada!");
                System.out.println(); // Blank line
            }


            rentalsServerIntf.reservaSombrinha(
                    LocalDateTime.of(2024, 1, 21, 8, 0),
                    "B",
                    1);
            rentalsServerIntf.reservaSombrinha(
                    LocalDateTime.of(2024, 1, 11, 8, 0),
                    "A",
                    1);

            // Print table de reservas que vem em String
            System.out.println(rentalsServerIntf.getReservasTable());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
