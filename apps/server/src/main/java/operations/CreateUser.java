import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateUser implements Operation {

  @Override
  public String run(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      CreateUserDTO req = objectMapper.readValue(rawJson, CreateUserDTO.class);

      String hashedPassword = BCrypt.hashpw(req.usuario.senha, BCrypt.gensalt());

      String err = UserRepository.createUser(req.usuario.nome, hashedPassword);
      if(err != null) return err;

    } catch(IOException e) {
      e.printStackTrace();
      return Failed.response(400);
    }

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "201";
  }
}
