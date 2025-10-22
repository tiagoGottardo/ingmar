import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

class ClientHandler extends Thread {
  private Socket socket;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
       BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
       
      String received;
      while ((received = in.readLine()) != null) {
        System.out.println(received);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
          JsonNode jsonNode = objectMapper.readTree(received); 

            
          String operation = jsonNode.get("operacao").asText();

          System.out.println("The operation is " + operation);

          if (operation == null) throw new Error("There is no operation!");

          out.println("{ \"status\": \"201\" }");
        } catch (IOException e) {
          out.println("{ \"status\": \"400\" }");
          e.printStackTrace();
        }

      }
    } catch (IOException e) {
      System.out.println("Client exception: " + e.getMessage());
    }
  }
}
