import java.rmi.*;

public class Server {
  public static void main(String[] args) {
    try {
      ServerImpl serverImpl = new ServerImpl();
      //Naming.rebind("Server", serverImpl);

      serverImpl.inicializacao();
      serverImpl.listarSombrinhas('C',1,1);
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}