import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

class Login implements Operation {
  @Override
  public String run(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      LoginDTO req = objectMapper.readValue(rawJson, LoginDTO.class);

      String sql = "SELECT id, password FROM users WHERE name = ?";

      try (Connection conn = Database.getInstance().getConnection();
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, req.usuario);

        try (ResultSet rs = pstmt.executeQuery()) {

          if (!rs.next()) return "{ \"status\": \"401\" }";

          String id = rs.getString("id");
          String hashedPassword = rs.getString("password");

          if (!BCrypt.checkpw(req.senha, hashedPassword)) return "{ \"status\": \"401\" }";

          return "{ \"status\": \"200\", \"token\": \"" + JWTUtil.generateToken(id) + "\" }";
        }

      } catch (SQLException e) {
          e.printStackTrace();
          return "{ \"status\": \"500\" }";
      }

    } catch (IOException e) {
      e.printStackTrace();
      return "{ \"status\": \"500\" }";
    }
  }
}
