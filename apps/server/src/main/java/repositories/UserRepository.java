import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

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

  public static String updateUserToken(int id, String token) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET token = ? WHERE id = ?;");

      pstmt.setString(1, token);
      pstmt.setInt(2, id);

      pstmt.executeUpdate();

      int rowsAffected = pstmt.executeUpdate();

      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  public static String removeUserToken(int id) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET token = NULL WHERE id = ?;");

      pstmt.setInt(1, id);

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

        int id = rs.getInt("id");
        String password = rs.getString("password");
        String token = rs.getString("token");

        return new Result(new User(id, name, password, token));
      }

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public Result<List<User>> getUsers() {
    List<User> users = new ArrayList<>();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users")) {
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {

        int id = rs.getInt("id");
        String name = rs.getString("name");

        users.add(new User(id, name));
      }

      return new Result<List<User>>(users);

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public Result<User> getUserById(int id) {
    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?")) {

      pstmt.setInt(1, id);

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

  static public String deleteUserById(int id) {

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

  static public String updateUserPassword(int id, String newPassword) {
    if(id == 1) return Failed.response(403);

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("UPDATE users SET password = ? WHERE id = ?")) {

      pstmt.setString(1, newPassword);
      pstmt.setInt(2, id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
      return Failed.response(500);
    }
  }

}
