import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class Register extends Command {

  @Override
  protected boolean validateInput(String args[]) {
    return true;
  }

  protected String run(String args[], TcpHandler tcpHandler) {

    Request request = new Request(args[1], args[2]);

    JsonNode response = mapJson(tcpHandler.sendMessage(request.toJson())); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 201) {
      System.out.println("Algo deu errado.");
      return response.get("message").asText();
    }

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
