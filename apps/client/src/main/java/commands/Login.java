import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class Login extends Command {

  Login(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  @Override
  protected boolean isAuth() { return false; } 

  protected String run(String args[], String token) {
    if(args.length != 3) return "Você não usou o comando 'login' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(args[1], args[2]);

    JsonNode response = mapJson(tcpHandler.sendMessage(request.toJson())); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200) {
      return response.get("mensagem").asText();
    }

    if (!TokenManager.saveToken(response.get("token").asText()))
      return "Algo deu errado ao salvar o token!";

    return "Login realizado com sucesso!";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "LOGIN";

    @JsonProperty
    private String usuario;

    @JsonProperty
    private String senha;

    Request(String name, String password) {
      this.usuario = name;
      this.senha = password;
    }
  }

}
