import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class AdminDeleteUser extends Command {
  AdminDeleteUser(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length != 2) return "Você não usou o comando 'deletar-usuario' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(args[1], token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    return "Você deletou o usuário com sucesso!";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "ADMIN_EXCLUIR_USUARIO";

    @JsonProperty
    private String id;

    @JsonProperty
    private String token;

    Request(String id, String token) {
      this.id = id;
      this.token = token;
    }
  }

}
