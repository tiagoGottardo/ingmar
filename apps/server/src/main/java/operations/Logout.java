import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

class Logout implements Operation {
  @Override
  public String run(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      LoginDTO req = objectMapper.readValue(rawJson, LoginDTO.class);

      return "{ \"status\": \"200\" }";

    } catch (IOException e) {
      e.printStackTrace();
      return "{ \"status\": \"500\" }";
    }
  }
}
