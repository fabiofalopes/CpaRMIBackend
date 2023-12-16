package Client;

import Server.RentalsServerIntf;

import java.rmi.*;
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

            System.out.println(rentalsServerIntf.listaConteudos());

            if (rentalsServerIntf.reservaSombrinha("B", 2, "10:00", "12:00")) {
                System.out.println("Reserva efetuada com sucesso!");
            } else {
                System.out.println("Reserva não efetuada!");
            }

            System.out.println(rentalsServerIntf.displayReserva("B", 2));
            System.out.println();
            System.out.println(rentalsServerIntf.displayReserva("C", 3));

            System.out.println(rentalsServerIntf.listaConteudos());
            // breakline
            System.out.println();
            System.out.println(rentalsServerIntf.getIdSombrinhasDisponivel());


            rentalsServerIntf.reservaSombrinha("B", 2, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("B", 2, "14:00", "15:00");
            rentalsServerIntf.reservaSombrinha("B", 2, "16:00", "17:00");
            rentalsServerIntf.reservaSombrinha("B", 2, "19:00", "20:00");
            rentalsServerIntf.reservaSombrinha("B", 2, "21:00", "22:00");
            rentalsServerIntf.reservaSombrinha("B", 3, "10:00", "11:00");
            rentalsServerIntf.reservaSombrinha("B", 4, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("B", 5, "10:00", "14:00");
            rentalsServerIntf.reservaSombrinha("B", 6, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("B", 7, "09:00", "11:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "15:00", "18:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "10:00", "12:00");
            rentalsServerIntf.reservaSombrinha("A", 8, "10:00", "12:00");



            System.out.println(rentalsServerIntf.getSombrinhasOcupadas());

            System.out.println(rentalsServerIntf.getReservasTable());

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}
