import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

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

  static public Result<User> getUserByName(String name) {
    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {

      pstmt.setString(1, name);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (!rs.next()) return new Result(Failed.response(401));

        String id = rs.getString("id");
        String password = rs.getString("password");
        String token = rs.getString("token");

        return new Result(new User(id, name, password, token));
      }

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }
}
