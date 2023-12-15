import java.rmi.*;

public interface ServerIntf extends Remote {

  void inicializacao() throws RemoteException;

  String reservarSombrinha(char praiaID, int sombrinhaID, int dia, int hora, int lotacao) throws RemoteException;

  String cancelarSombrinha(int sombrinhaID) throws RemoteException;

  String listarSombrinhas() throws RemoteException;
}
