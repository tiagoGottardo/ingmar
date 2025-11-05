import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ReviewRepository {

  public static String createReview (Review review) {
    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO reviews (movie_id, title, username, rating, description) VALUES (?, ?, ?, ?, ?)");

      pstmt.setInt(1, review.movie_id);
      pstmt.setString(2, review.title);
      pstmt.setString(3, review.username);
      pstmt.setInt(4, review.rating);
      pstmt.setString(5, review.description);

      pstmt.executeUpdate();

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

}
