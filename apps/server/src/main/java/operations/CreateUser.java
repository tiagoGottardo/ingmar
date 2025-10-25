import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class CreateUser implements Operation {

  @Override
  public String run(JsonNode request) {
    String hashedPassword = BCrypt.hashpw(request.get("usuario").get("senha").asText(), BCrypt.gensalt());

    String err = UserRepository.createUser(request.get("usuario").get("nome").asText(), hashedPassword);
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "201";
  }
}
