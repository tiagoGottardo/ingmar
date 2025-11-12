import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class MovieRepository {

  public static String createMovie (Movie movie) {

    try (Connection conn = Database.getInstance().getConnection()) {

      PreparedStatement pstmt = conn.prepareStatement("INSERT INTO movies (title, director, year, genre, synopsis) VALUES (?, ?, ?, ?, ?)");

      pstmt.setString(1, movie.title);
      pstmt.setString(2, movie.director);
      pstmt.setInt(3, movie.year);
      pstmt.setArray(4, conn.createArrayOf("TEXT", movie.genre));
      pstmt.setString(5, movie.synopsis);

      pstmt.executeUpdate();

      return null;

    } catch(SQLException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  static public String updateMovie(Movie movie) {
    if(movie.id == 0)  return Failed.response(500);

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("UPDATE movies SET title = ?, director = ?, year = ?, genre = ?, synopsis = ? WHERE id = ?")) {

      pstmt.setString(1, movie.title);
      pstmt.setString(2, movie.director);
      pstmt.setInt(3, movie.year);
      pstmt.setArray(4, conn.createArrayOf("TEXT", movie.genre));
      pstmt.setString(5, movie.synopsis);

      pstmt.setInt(6, movie.id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
      return Failed.response(500);
    }
  }
  
  static public Result<Movie> getMovieById(int id) {
    Movie movie = new Movie();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movies WHERE id = ?")) {

      pstmt.setInt(1, id);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (!rs.next()) return new Result(Failed.response(404));

        movie.title = rs.getString("title");
        movie.director = rs.getString("director");
        movie.year = rs.getInt("year");
        movie.synopsis = rs.getString("synopsis");

        Array genreArray = rs.getArray("genre");
        if (genreArray == null) return new Result(Failed.response(500));
        movie.genre = (String[]) genreArray.getArray();

        return new Result(movie);
      }

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public Result<List<Movie>> getMovies() {
    List<Movie> movies = new ArrayList<>();

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM movies")) {
      ResultSet rs = pstmt.executeQuery();

      while (rs.next()) {
        Movie movie = new Movie();

        movie.id = rs.getInt("id");
        movie.title = rs.getString("title");
        movie.director = rs.getString("director");
        movie.year = rs.getInt("year");
        movie.synopsis = rs.getString("synopsis");

        Array genreArray = rs.getArray("genre");

        if (genreArray == null) return new Result(Failed.response(500));

        movie.genre = (String[]) genreArray.getArray();

        movies.add(movie);
      }

      return new Result<List<Movie>>(movies);

    } catch (SQLException e) {
        e.printStackTrace();
        return new Result(Failed.response(500));
    }
  }

  static public String deleteMovieById(int id) {

    try (Connection conn = Database.getInstance().getConnection();
      PreparedStatement pstmt = conn.prepareStatement("DELETE FROM movies WHERE id = ?")) {

      pstmt.setInt(1, id);

      int rowsAffected = pstmt.executeUpdate();
      if (rowsAffected == 0) return Failed.response(404); 

      return null;

    } catch (SQLException e) {
        e.printStackTrace();
        return Failed.response(500);
    }
  }

}
