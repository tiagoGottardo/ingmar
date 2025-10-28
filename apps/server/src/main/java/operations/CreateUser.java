import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class CreateUser extends Operation {
  @Override
  protected boolean isAuth() { return false; } 

  @Override
  public String run(Context ctx) {
    String validationError = Validator.validateName(ctx.request.get("usuario").get("nome").asText());
    if(validationError != null) return Failed.response(400, validationError);
    validationError = Validator.validatePassword(ctx.request.get("usuario").get("senha").asText());
    if(validationError != null) return Failed.response(400, validationError);

    String hashedPassword = BCrypt.hashpw(ctx.request.get("usuario").get("senha").asText(), BCrypt.gensalt());

    String err = UserRepository.createUser(ctx.request.get("usuario").get("nome").asText(), hashedPassword);
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "201";

    @JsonProperty
    final private String mensagem = "Conta criada com sucesso!";
  }
}
