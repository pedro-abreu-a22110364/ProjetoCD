import java.io.FileNotFoundException;
import java.rmi.*;

public interface ServerIntf extends Remote {

  String inicializarSombrinhas();

  String carregarReservas();

  String importarReserva(String linha);

  String reservarSombrinha(String praiaID, int dia, int hora, int lotacao) throws RemoteException;

  String cancelarSombrinha(int sombrinhaID) throws RemoteException;

  String listarSombrinhas(char praiaID, int dia, int hora) throws RemoteException;
}
