import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

class Failed {
  public static final Map<Integer, String> DEFAULT_MESSAGE = Map.of(
    400, "Não foi possível processar a requisição devido a um formato incorreto ou dados inválidos",
    401, "Credenciais Inválidas",
    403, "Você não tem permissão para executar essa ação",
    404, "O item que você tentou acessar não existe",
    405, "Algum campo não está preenchido corretamente",
    409, "Instância já existe",
    422, "Dados faltantes",
    500, "Erro Interno do Servidor"
  );

  @JsonProperty
  private String status;

  @JsonProperty
  private String mensagem;

  private Failed(int status, String message) {
    this.status = String.valueOf(status);
    this.mensagem = message;
  }

  private Failed(int status) {
    this.status = String.valueOf(status);
    this.mensagem = DEFAULT_MESSAGE.get(status); 
  }

  private String toJson() {
    try {
      return (new ObjectMapper()).writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  static public String response (int status) {
    return new Failed(status).toJson();
  }

  static public String response (int status, String message) {
    return new Failed(status, message).toJson();
  }

}
