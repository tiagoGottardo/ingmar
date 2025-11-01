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

  public static String updateUserToken(String id, String token) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET token = ? WHERE id = ?;");

      pstmt.setString(1, token);
      pstmt.setInt(2, Integer.parseInt(id));

      pstmt.executeUpdate();

      int rowsAffected = pstmt.executeUpdate();

      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  public static String removeUserToken(String id) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET token = NULL WHERE id = ?;");

      pstmt.setInt(1, Integer.parseInt(id));

      pstmt.executeUpdate();

      int rowsAffected = pstmt.executeUpdate();

      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  static public Result<User> getUserByName(String name) {
    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE name = ?")) {

      pstmt.setString(1, name);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (!rs.next()) return new Result(Failed.response(404));

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

  static public Result<User> getUserById(String id) {
    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {

      pstmt.setInt(1, Integer.parseInt(id));

      try (ResultSet rs = pstmt.executeQuery()) {

        if (!rs.next()) return new Result(Failed.response(404));

        String name = rs.getString("name");
        String password = rs.getString("password");
        String token = rs.getString("token");

        return new Result(new User(id, name, password, token));
      }

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public String deleteUserById(String strId) {
    int id = Integer.parseInt(strId);

    if(id == 1) return Failed.response(403);

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE id = ?")) {

      pstmt.setInt(1, id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
        e.printStackTrace();
        return Failed.response(500);
    }
  }

  static public String updateUserById(String strId, String newName, String newPassword) {
    int id = Integer.parseInt(strId);

    if(id == 1) return Failed.response(403);

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET name = ?, password = ? WHERE id = ?")) {

      pstmt.setString(1, newName);
      pstmt.setString(2, newPassword);
      pstmt.setInt(3, id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
      e.printStackTrace();
      if ("23505".equals(e.getSQLState())) 
        return Failed.response(409, "Alguem já usa esse nome"); 
      
      return Failed.response(500);
    }
  }

}
