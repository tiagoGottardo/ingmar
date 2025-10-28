import java.net.InetAddress;
import java.net.ServerSocket;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    String hostname = (args.length > 0) ? args[0] : "0.0.0.0";
    int backlog = 50; // Max pending connections queue
    int port = (args.length > 1) ? Integer.parseInt(args[1]) : 8080; 

    try (ServerSocket serverSocket = new ServerSocket(port, backlog, InetAddress.getByName(hostname))) {
      System.out.println("Server is listening on " + hostname + ":" + port);
      
      while (true) new ClientHandler(serverSocket.accept()).start();
      
    } catch (IOException e) {
      System.out.println("Server exception: " + e.getMessage());
    }
  }
}
