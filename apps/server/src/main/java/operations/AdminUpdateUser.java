import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class AdminUpdateUser extends Operation {
  @Override
  protected boolean mustBeAdmin() { return true; } 

  @Override
  public String run(Context ctx) {
    String newPassword = BCrypt.hashpw(ctx.request.get("usuario").get("senha").asText(), BCrypt.gensalt());
    String userId = ctx.request.get("id").asText();

    String err = UserRepository.updateUserPassword(userId, newPassword);
    if (err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";

    @JsonProperty
    final private String mensagem = "Operação executada com sucesso!";
  }
}
