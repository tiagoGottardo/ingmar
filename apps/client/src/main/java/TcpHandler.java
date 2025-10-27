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

  public void connect(String hostname, int port) {
    if (hostname == null || hostname.isEmpty()) {
      System.out.println("Hostname cannot be null or empty");
      return;
    }
    
    if (port <= 0 || port > 65535) {
      System.out.println("Port number must be between 1 and 65535");
      return;
    }

    System.out.println("Connecting to " + hostname + ":" + port);
    close(); 
    try {
      this.socket = new Socket(hostname, port);
      this.writer = new PrintWriter(socket.getOutputStream(), true);
      this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      System.out.println("Connection established.");
    } catch (IOException e) {
      System.err.println("Connection failed: " + e.getMessage());
    }
  }

  public String sendMessage(String message) {
    synchronized (ioLock) {
      if (!isConnected()) 
        return "You do not connect to the server!"; 
        
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
        return "Communication error: " + e.getMessage();
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
