import java.rmi.*;
import java.rmi.server.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerImpl extends UnicastRemoteObject
  implements ServerIntf {

  public static ArrayList<Praia> praias = new ArrayList<>();
  public static HashMap<String,Sombrinha> reservas = new HashMap<>();

  public ServerImpl() throws RemoteException {
  }

  @Override
  public void inicializacao() throws RemoteException {

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
  public String reservarSombrinha(char praiaID, int dia, int hora, int lotacao) throws RemoteException {
    //ler do ficheiro
    //guardo no hash map
    //escrevo no ficheiro


    return null;
  }

  @Override
  public String cancelarSombrinha(int sombrinhaID) throws RemoteException {
    return null;
  }

  @Override
  public String listarSombrinhas(char praiaID, int dia, int hora) throws RemoteException {
    //listar sombrinhas não reservadas numa praia, na data e hora proposta e estando o utilizador autenticado
    Praia praiaTemp = null;
    
    switch (praiaID){
      case 'A':
        praiaTemp = praias.get(0);
        break;
      case 'B':
        praiaTemp = praias.get(1);
        break;
      case 'C':
        praiaTemp = praias.get(2);
        break;
      default:
        //erro praia invalida
        System.out.println("Praia inválida");
        break;
      }

    for (Sombrinha sombrinha : praiaTemp.sombrinhas) {

    }

    return null;
  }
}
