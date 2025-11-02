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
    Map<String, Operation> operations = Map.of(
      "CRIAR_USUARIO", new CreateUser(),
      "LOGIN", new Login(),
      "LOGOUT", new Logout(),
      "LISTAR_PROPRIO_USUARIO", new Me(),
      "EXCLUIR_PROPRIO_USUARIO", new DeleteUser(),
      "EDITAR_PROPRIO_USUARIO", new UpdateUser(),
      "LISTAR_USUARIOS", new GetAllUsers(),
      "ADMIN_EDITAR_USUARIO", new AdminUpdateUser(),
      "ADMIN_EXCLUIR_USUARIO", new AdminDeleteUser()
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
