import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;

class AdminUpdateUser extends Command {
  AdminUpdateUser(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length != 3) return "Você não usou o comando 'atualizar-usuario' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(args[1], args[2], token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    return "Você atualizou o usuário com sucesso!";
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private class User {
    public String senha;
    User(String password) { this.senha = password; }
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "ADMIN_EDITAR_USUARIO";

    @JsonProperty
    private User usuario;

    @JsonProperty
    private String id;

    @JsonProperty
    private String token;

    Request(String id, String password, String token) {
      this.id = id;
      this.usuario = new User(password);
      this.token = token;
    }
  }

}
