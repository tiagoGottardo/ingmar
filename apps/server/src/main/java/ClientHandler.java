import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;

class ClientHandler extends Thread {
  private Socket socket;

  public ClientHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    Map<String, Operation> operations = Map.ofEntries(
      Map.entry("CRIAR_USUARIO", new CreateUser()),
      Map.entry("LOGIN", new Login()),
      Map.entry("LOGOUT", new Logout()),
      Map.entry("LISTAR_PROPRIO_USUARIO", new Me()),
      Map.entry("EXCLUIR_PROPRIO_USUARIO", new DeleteUser()),
      Map.entry("EDITAR_PROPRIO_USUARIO", new UpdateUser()),
      Map.entry("LISTAR_USUARIOS", new GetAllUsers()),
      Map.entry("ADMIN_EDITAR_USUARIO", new AdminUpdateUser()),
      Map.entry("ADMIN_EXCLUIR_USUARIO", new AdminDeleteUser()),
      Map.entry("CRIAR_FILME", new CreateMovie()),
      Map.entry("EDITAR_FILME", new UpdateMovie()),
      Map.entry("LISTAR_FILMES", new GetAllMovies())
    );

    try (
      Socket socket = this.socket; 
      PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
    ) {
      ObjectMapper objectMapper = new ObjectMapper();
      String rawRequest;

      while ((rawRequest = in.readLine()) != null) {
        try {
          JsonNode request = objectMapper.readTree(rawRequest); 

          System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request));

          String operation = request.get("operacao").asText();

          if (operation == null) {
            out.println(Failed.response(400, "Você precisa especificar sua operação."));
            continue;
          }

          String response = operations.get(operation).start(request); 

          out.println(response);

          System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectMapper.readTree(response)));

          if((operation.equals("EXCLUIR_PROPRIO_USUARIO") || operation.equals("LOGOUT")) && objectMapper.readTree(response).get("status").asText().equals("200")) break;

        } catch (IOException e) {
          out.println(Failed.response(400));
          e.printStackTrace();
        }
      }

    } catch (IOException e) {
      System.out.println("Client exception: " + e.getMessage());
    }
  }
}
