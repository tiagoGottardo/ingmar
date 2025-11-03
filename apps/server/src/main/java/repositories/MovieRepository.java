import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

class MovieRepository {

  public static String createMovie (Movie movie) {
    int movieYear = Integer.parseInt(movie.year);

    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO movies (title, director, year, genre, synopsis) VALUES (?, ?, ?, ?, ?)");

      pstmt.setString(1, movie.title);
      pstmt.setString(2, movie.director);
      pstmt.setInt(3, movieYear);
      pstmt.setArray(4, conn.createArrayOf("TEXT", movie.genre));
      pstmt.setString(5, movie.synopsis);

      pstmt.executeUpdate();

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

}
