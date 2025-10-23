import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;

class CreateUser implements Operation {

  @Override
  public String run(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();

    try {
      CreateUserDTO req = objectMapper.readValue(rawJson, CreateUserDTO.class);

      String hashedPassword = BCrypt.hashpw(req.usuario.senha, BCrypt.gensalt());

      try (Connection conn = Database.getInstance().getConnection()) {

        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");

        pstmt.setString(1, req.usuario.nome);
        pstmt.setString(2, hashedPassword);

        pstmt.executeUpdate();

      } catch(SQLException e) {
        if (e.getSQLState().equals("23505")) return "{ \"status\": \"409\", \"message\": \"User already exists.\" }";

        e.printStackTrace();
        return "{ \"status\": \"500\" }";
      }

    } catch(IOException e) {
      e.printStackTrace();
      return "{ \"status\": \"400\" }";
    }

    return "{ \"status\": \"201\" }";
  }
}
