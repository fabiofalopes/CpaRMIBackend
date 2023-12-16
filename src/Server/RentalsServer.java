package Server;

import org.w3c.dom.css.Counter;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RentalsServer {

    public static void main(String args[]) {
        try {
            //RentalsServerImpl rentalsServerImpl = new RentalsServerImpl();
            //Naming.rebind("RentalsServer", rentalsServerImpl);
            Registry r = LocateRegistry.createRegistry(1099);

            RentalsServerImpl rentalsServerImpl = new RentalsServerImpl();
            r.rebind("RentalsServer", rentalsServerImpl);
        } catch (Exception e) {
            System.out.println("Exception Server: " + e);
        }
    }
}
