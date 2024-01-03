import java.rmi.*;

public class Server {
  public static void main(String[] args) {
    try {
      ServerImpl serverImpl = new ServerImpl();
      //Naming.rebind("Server", serverImpl);

      serverImpl.inicializarSombrinhas();
      serverImpl.carregarReservas();

      serverImpl.reservarSombrinha("A",30,8,8);
      serverImpl.reservarSombrinha("A",30,8,2);
      serverImpl.cancelarSombrinha("A",10,30,8);
      serverImpl.listarSombrinhas("A",30,8);
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}