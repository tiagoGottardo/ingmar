import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class UpdateUser extends Operation {
  @Override
  public String run(Context ctx) {
    if(ctx.request.get("usuario").get("nome") != null) {
      String nameValidationError = Validator.validateName(ctx.request.get("usuario").get("nome").asText());
      if(nameValidationError != null) return Failed.response(400, nameValidationError);
    }

    if(ctx.request.get("usuario").get("senha") != null) {
      String passwordValidationError = Validator.validatePassword(ctx.request.get("usuario").get("senha").asText());
      if(passwordValidationError != null) return Failed.response(400, passwordValidationError);
    }

    String newPassword = (ctx.request.get("usuario").get("senha") != null) 
    ? BCrypt.hashpw(ctx.request.get("usuario").get("senha").asText(), BCrypt.gensalt()) 
    : ctx.user.password;

    String newName = (ctx.request.get("usuario").get("nome") != null) 
    ? ctx.request.get("usuario").get("nome").asText()
    : ctx.user.name;

    String err = UserRepository.updateUserById(ctx.user.id, newName, newPassword);
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
