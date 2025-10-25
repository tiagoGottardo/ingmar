import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

public class Login implements Operation {
  @Override
  public String run(JsonNode request) {
    Result<User> result = UserRepository.getUserByName(request.get("usuario").asText());
    if(result.err != null) return result.err;

    User user = result.value;

    if (!BCrypt.checkpw(request.get("senha").asText(), user.password)) return Failed.response(401); 

    return new Success(JWTUtil.generateToken(user.id)).toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    private String token;

    @JsonProperty
    final private String status = "200";

    public Success(String token) {
      this.token = token;
    }
  }
}

