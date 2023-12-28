package Server;

import java.rmi.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public interface RentalsServerIntf extends Remote{
    String testRMI() throws RemoteException;
    String listAllMethods() throws RemoteException;
    int reservaSombrinha(LocalDateTime hora, String idPraia, int id_sombrinha) throws RemoteException;
    int cancelaReserva(LocalDateTime hora , String idPraia, int idSombrinha) throws RemoteException;
    ArrayList<String> listaSombrinhasDisponiveis(LocalDateTime hora , String idPraia) throws RemoteException;
    String getReservasTable() throws RemoteException;
}

