import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TcpHandler {
  private Socket socket;
  private PrintWriter writer;
  private BufferedReader reader;
  private final Object ioLock = new Object();

  public String validateAndConnect(String input) {
    String[] args = input.split(" ");

    if(args.length != 3)
      return "Você não usou o comando 'conectar' da forma correta\nUse o comando 'ajuda'";

    String hostname = args[1];
    String port = args[2];

    String[] parts = hostname.split("\\.");

    try {
      for (String part : parts) {
        int p = Integer.parseInt(part);
        if (p < 0 || p > 255) return "Endereço inválido!";
      }
    } catch (NumberFormatException e) {
      return "Endereço inválido!";
    }

    try {
      int portInt = Integer.parseInt(port); 
      if (portInt < 0 || portInt > 65535) return "Porta inválida!";

      // connect("127.0.0.1", 8080);
      connect(hostname, portInt);

    } catch (NumberFormatException e) {
      return "Porta inválida!";
    }

    return null;
  }

  public void connect(String hostname, int port) {
    if (hostname == null || hostname.isEmpty()) {
      System.out.println("Hostname cannot be null or empty");
      return;
    }
    
    if (port <= 0 || port > 65535) {
      System.out.println("Port number must be between 1 and 65535");
      return;
    }

    System.out.println("Conectando -> " + hostname + ":" + port);
    close(); 
    try {
      this.socket = new Socket(hostname, port);
      this.writer = new PrintWriter(socket.getOutputStream(), true);
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("Conexão estabelecida.");
    } catch (IOException e) {
      System.err.println("Conexão recusada em " + hostname + ":" + port);
    }
  }

  public String sendMessage(String message) {
    synchronized (ioLock) {
      if (!isConnected()) 
        return "Você não está conectado em um servidor!"; 
        
      try {
        writer.println(message);

        if (writer.checkError()) 
          return "PrintWriter error: Connection may be lost.";

        String response = reader.readLine(); 
        
        if (response == null) 
          return "Server closed the connection (read null).";
        
        return response;

      } catch (IOException e) {
        close(); 
        return "Algo deu errado na comunicação com o servidor: " + e.getMessage();
      }
    }
  }
    
  public boolean isConnected() {
    return this.socket != null && !this.socket.isClosed() && this.socket.isConnected();
  }
  
  public void close() {
    try { if (writer != null) writer.close(); } catch (Exception e) {}
    try { if (reader != null) reader.close(); } catch (Exception e) {}
    try { if (socket != null) socket.close(); } catch (IOException e) {}
  }
}
