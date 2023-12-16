package Server;

import Reservation.Reserva;
import Reservation.ReservaUtility;

import java.util.Timer;
import java.util.TimerTask;


import java.lang.reflect.Method;
import java.rmi.*;
import java.rmi.server.*;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RentalsServerImpl extends UnicastRemoteObject  implements RentalsServerIntf {

    ReservaUtility reservaUtility;

    RentalsServerImpl() throws RemoteException {
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


    public boolean reservaSombrinha(String idPraia, int id_sombrinha, String horaInicio, String horaFim) throws RemoteException{
        if (reservaUtility.reservaSombrinha(idPraia, id_sombrinha, horaInicio, horaFim)){
            logger.log(Level.INFO, "reservaSombrinha method was called."); // Log message when method is called
            return true;
        }
        return false;
    }
    public String displayReserva(String idPraia, int id_sombrinha) throws RemoteException{
        return reservaUtility.toStringReserva(idPraia, id_sombrinha);
    }


    public String getIdSombrinhasDisponivel() throws RemoteException{
        return reservaUtility.getIdSombrinhasDisponivel();
    }

    public String getSombrinhasOcupadas() throws RemoteException{
        return reservaUtility.getSombrinhasOcupadas();
    }

    public String listaConteudos() throws RemoteException{
        return reservaUtility.toStringAll();
    }

    public String getReservasTable() throws RemoteException{
        return reservaUtility.toStringTable();
    }

}
