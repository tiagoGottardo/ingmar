import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class Me extends Command {
  Me(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  @Override
  protected boolean validateInput(String args[]) {
    return true;
  }

  protected String run(String args[], String token) {
    Request request = new Request(token);

    JsonNode response = mapJson(tcpHandler.sendMessage(request.toJson())); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("message").asText();

    return "Você está logado com o usuário: " + response.get("user").asText();
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "LISTAR_PROPRIO_USUARIO";

    @JsonProperty
    private String token;

    Request(String token) {
      this.token = token;
    }
  }

}
