import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;

public class ServerImpl extends UnicastRemoteObject
  implements ServerIntf {

  public ServerImpl() throws RemoteException {
  }

  @Override
  public void inicializacao() throws RemoteException {
    ArrayList<Praia> praias = new ArrayList<>();

    // Criação das praias
    Praia praiaA = new Praia('A');
    Praia praiaB = new Praia('B');
    Praia praiaC = new Praia('C');

    // Criação das sombrinhas da praia A
    for (int i = 1; i <= 10; i++){
      praiaA.adicionarSombrinha(new Sombrinha(i,2));
    }

    for (int i = 11; i <= 15; i++){
      praiaA.adicionarSombrinha(new Sombrinha(i,3));
    }

    for (int i = 16; i <= 20; i++){
      praiaA.adicionarSombrinha(new Sombrinha(i,4));
    }

    // Criação das sombrinhas da praia B
    for (int i = 1; i <= 5; i++){
      praiaB.adicionarSombrinha(new Sombrinha(i,2));
    }

    for (int i = 6; i <= 10; i++){
      praiaB.adicionarSombrinha(new Sombrinha(i,3));
    }

    praiaB.adicionarSombrinha(new Sombrinha(11,4));

    // Criação das sombrinhas da praia C
    for (int i = 1; i <= 10; i++){
      praiaC.adicionarSombrinha(new Sombrinha(i,2));
    }

    // Adicionar as praias ao arraylist das praias
    praias.add(praiaA);
    praias.add(praiaB);
    praias.add(praiaC);

  }

  @Override
  public String reservarSombrinha(char praiaID, int sombrinhaID, int dia, int hora, int lotacao) throws RemoteException {
    return null;
  }

  @Override
  public String cancelarSombrinha(int sombrinhaID) throws RemoteException {
    return null;
  }

  @Override
  public String listarSombrinhas() throws RemoteException {
    return null;
  }
}
