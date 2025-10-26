import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class UpdateUser extends Operation {
  @Override
  public String run(Context ctx) {

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
  }
}
