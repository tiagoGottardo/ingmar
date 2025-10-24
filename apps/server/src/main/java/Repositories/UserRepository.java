import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;

class UserRepository {

  public static String createUser (String name, String password) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users (name, password) VALUES (?, ?)");

      pstmt.setString(1, name);
      pstmt.setString(2, password);

      pstmt.executeUpdate();

      return null;

    } catch(SQLException e) {
      if (e.getSQLState().equals("23505")) return Failed.response(409);


      e.printStackTrace();
      return Failed.response(500);
    }
  }

}
