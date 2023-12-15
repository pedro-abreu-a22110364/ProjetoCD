import java.rmi.*;
public class Server {
  public static void main(String[] args) {
    try {
      ServerImpl addServerImpl = new ServerImpl();
      Naming.rebind("AddServer", addServerImpl);
    }
    catch(Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}