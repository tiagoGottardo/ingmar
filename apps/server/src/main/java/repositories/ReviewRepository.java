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

  static public String updateReview(Review review) {
    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("UPDATE reviews SET movie_id = ?, title = ?, rating = ?, description = ? WHERE id = ?")) {

      pstmt.setInt(1, review.movie_id);
      pstmt.setString(2, review.title);
      pstmt.setInt(3, review.rating);
      pstmt.setString(4, review.description);

      pstmt.setInt(5, review.id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
      return Failed.response(500);
    }
  }
  
  static public Result<Review> getReviewById(int id) {
    Review review = new Review();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reviews WHERE id = ?")) {

      pstmt.setInt(1, id);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (!rs.next()) return new Result(Failed.response(404));

        review.id = id;
        review.movie_id = rs.getInt("movie_id");
        review.title = rs.getString("title");
        review.username = rs.getString("username");
        review.rating = rs.getInt("rating");
        review.description = rs.getString("description");

        return new Result(review);
      }

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public Result<List<Review>> getReviewsByMovieId(int movie_id) {
    List<Review> reviews = new ArrayList<>();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reviews WHERE movie_id = ?")) {

      pstmt.setInt(1, movie_id);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Review review = new Review();

        review.id = rs.getInt("movie_id");
        review.movie_id = rs.getInt("movie_id");
        review.title = rs.getString("title");
        review.username = rs.getString("username");
        review.rating = rs.getInt("rating");
        review.description = rs.getString("description");

        reviews.add(review);
      }

      return new Result<List<Review>>(reviews);

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public Result<List<Review>> getReviewsByUsername(String username) {
    List<Review> reviews = new ArrayList<>();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM reviews WHERE username = ?")) {

      pstmt.setString(1, username);

      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Review review = new Review();

        review.id = rs.getInt("movie_id");
        review.movie_id = rs.getInt("movie_id");
        review.title = rs.getString("title");
        review.username = rs.getString("username");
        review.rating = rs.getInt("rating");
        review.description = rs.getString("description");

        reviews.add(review);
      }

      return new Result<List<Review>>(reviews);

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

}
