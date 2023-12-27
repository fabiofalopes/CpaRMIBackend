package Server;

import Reservation.ReservaUtility;

import java.lang.reflect.Method;
import java.rmi.*;
import java.rmi.server.*;
import java.time.LocalDateTime;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RentalsServerImpl extends UnicastRemoteObject  implements RentalsServerIntf {

    ReservaUtility reservaUtility;

    public RentalsServerImpl() throws RemoteException {
        super();
        this.reservaUtility = new ReservaUtility();
    }

    private static final Logger logger = Logger.getLogger(RentalsServerImpl.class.getName());

    static {
        try {
            FileHandler fh = new FileHandler("server.log", true);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String testRMI() throws RemoteException{
        logger.log(Level.INFO, "testRMI method was called."); // Log message when method is called
        return "RMI Hello World!";
    }

    public String listAllMethods() throws RemoteException {
        StringBuilder methodsString = new StringBuilder();
        int counter = 1;

        methodsString.append("List RMI Methods:").append("\n");

        for (Method method : RentalsServerIntf.class.getMethods()) {
            methodsString.append(counter + ": ").append(method).append("\n");
            counter++;
        }
        return methodsString.toString();
    }

    public boolean reservaSombrinha(LocalDateTime hora, String idPraia, int id_sombrinha) throws RemoteException{
        if (reservaUtility.inserirReserva(hora, idPraia, id_sombrinha)){
            // Log message when method is called
            logger.log(Level.INFO, "reservaSombrinha method was called.");
            return true;
        }
        return false;
    }

    public String getReservasTable() throws RemoteException{
        return reservaUtility.toStringTable();
    }

}
