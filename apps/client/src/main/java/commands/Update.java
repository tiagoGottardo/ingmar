import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.IOException;

class Update extends Command {
  Update(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length() != 3) return "Você não usou o comando 'atualizar' da forma correta\nUse o comando 'ajuda'";

    if(!args[1].equals("nome") && !args[1].equals("senha")) 
      return "Você pode atualizar nome ou senha.\nVeja o comando 'ajuda'";

    User user = new User();

    if(args[1].equals("nome")) user.nome = args[2];
    if(args[1].equals("senha")) user.senha = args[2];

    Request request = new Request(user, token);

    JsonNode response = mapJson(tcpHandler.sendMessage(request.toJson())); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("message").asText();

    return "Você atualizou o " + args[1] + " com sucesso!";
  }

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private class User {
    public String nome;
    public String senha;
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "EDITAR_PROPRIO_USUARIO";

    @JsonProperty
    private String token;

    @JsonProperty
    private User usuario;

    Request(User usuario, String token) {
      this.usuario = usuario;
      this.token = token;
    }
  }

}
