import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class ShowUsers extends Command {
  ShowUsers(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length != 1) return "Você não usou o comando 'listar-usuarios' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    User[] users = User.fromJsonArray(response.get("usuarios"));

    System.out.println(String.format("| %-3s | %-20s |", "Id", "Nome"));
    System.out.println("-".repeat(30));

    for(User user : users) System.out.println(user.toString());

    return "";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "LISTAR_USUARIOS";

    @JsonProperty
    private String token;

    Request(String token) {
      this.token = token;
    }
  }

}
