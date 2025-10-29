import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class Delete extends Command {
  Delete(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length != 1) return "Você não usou o comando 'deletar' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    TokenManager.deleteToken();

    System.out.println("Seu usuário foi deletado!");

    tcpHandler.close();

    return "Você fechou a conexão com o servidor!";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "EXCLUIR_PROPRIO_USUARIO";

    @JsonProperty
    private String token;

    Request(String token) {
      this.token = token;
    }
  }

}
