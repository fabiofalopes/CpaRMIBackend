package Server;

import Reservation.ReservaUtility;

import java.lang.reflect.Method;
import java.rmi.*;
import java.rmi.server.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    public int reservaSombrinha(LocalDateTime hora, String idPraia, int id_sombrinha) throws RemoteException{
        int id = reservaUtility.inserirNovaReserva(hora, idPraia, id_sombrinha);
        logger.log(Level.INFO, "reservaSombrinha method was called.");
        return id;
    }

    public int cancelaReserva(LocalDateTime hora , String idPraia, int idSombrinha) throws RemoteException{
        logger.log(Level.INFO, "cancelaReserva method was called.");
        return reservaUtility.removeReserva(hora, idPraia, idSombrinha);
    }

    public String listaSombrinhasDisponiveis(int horario, String data, String idPraia) throws RemoteException{
        LocalDateTime horaFinal = LocalDateTime.of(Integer.parseInt(data.split("-")[0]), Integer.parseInt(data.split("-")[1]), Integer.parseInt(data.split("-")[2]), horario, 0);
        logger.log(Level.INFO, "listaSombrinhasDisponiveis method was called.");
        logger.log(Level.INFO, "hora: " + horaFinal);
        logger.log(Level.INFO, "idPraia: " + idPraia);
        logger.log(Level.INFO, "listaSombrinhasDisponiveis: " + reservaUtility.listaSombrinhasDisponiveis2(horaFinal, idPraia));
        return reservaUtility.listaSombrinhasDisponiveis2(horaFinal, idPraia);
    }

    public String getReservasTable() throws RemoteException{
        return reservaUtility.toStringTable();
    }
}
