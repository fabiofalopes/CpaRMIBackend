package Server;

import java.rmi.*;

public interface RentalsServerIntf extends Remote{
    String testRMI() throws RemoteException;
    String listAllMethods() throws RemoteException;
    boolean reservaSombrinha(String idPraia, int id_sombrinha, String horaInicio, String horaFim) throws RemoteException;
    String displayReserva(String idPraia, int id_sombrinha) throws RemoteException;

    //boolean cancelaReserva(String idPraia, int id_sombrinha) throws RemoteException;
    //boolean alteraReserva(String idPraia, int id_sombrinha, String horaInicio, String horaFim) throws RemoteException;
    //boolean verificaDisponibilidade(String idPraia, int id_sombrinha, String horaInicio, String horaFim) throws RemoteException;

    String getIdSombrinhasDisponivel() throws RemoteException;

    String getSombrinhasOcupadas() throws RemoteException;

    String  listaConteudos() throws RemoteException;
    String getReservasTable() throws RemoteException;
}

