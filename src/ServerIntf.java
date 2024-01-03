import java.rmi.*;

public interface ServerIntf extends Remote {

  String inicializarSombrinhas();

  String carregarReservas();

  String atualizarReservas();

  String reservarSombrinha(String praiaID, int dia, int hora, int lotacao) throws RemoteException;

  String cancelarSombrinha(String praiaID, int sombrinhaID, int dia, int hora) throws RemoteException;

  String listarSombrinhas(String praiaID, int dia, int hora) throws RemoteException;
}
