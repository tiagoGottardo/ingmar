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

public class Login extends Operation {
  @Override
  protected boolean isAuth() { return false; } 

  @Override
  public String run(Context ctx) {
    Result<User> result = UserRepository.getUserByName(ctx.request.get("usuario").asText());
    if(result.err != null) return result.err;

    User user = result.value;

    if(user.token != null) return Failed.response(400, "You can't login twice in a row.");

    if(!BCrypt.checkpw(ctx.request.get("senha").asText(), user.password)) return Failed.response(401); 

    String newToken = JWTUtil.generateToken(user.id);

    String err = UserRepository.updateUserToken(user.id, newToken);
    if(err != null) return err;

    return new Success(newToken).toJson();
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

