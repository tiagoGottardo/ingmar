import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class Register extends Command {

  Register(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  @Override
  protected boolean isAuth() { return false; } 

  protected String run(String args[], String token) {
    if(args.length != 3) return "Você não usou o comando 'registrar' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(args[1], args[2]);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 201) 
      return response.get("mensagem").asText();

    return "Usuário criado com sucesso";
  }

  private class User {
    @JsonProperty
    private String nome;

    @JsonProperty
    private String senha;

    User(String nome, String senha) {
      this.nome = nome;
      this.senha = senha;
    }

  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "CRIAR_USUARIO";

    @JsonProperty
    private User usuario;

    Request(String name, String password) {
      this.usuario = new User(name, password);
    }
  }

}
