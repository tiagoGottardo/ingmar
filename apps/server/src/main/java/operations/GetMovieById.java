import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class GetMovieById extends Operation {
  @Override
  public String run(Context ctx) {

    Result<Movie> movieResult = MovieRepository.getMovieById(ctx.request.get("id_filme").asText());
    if(movieResult.err != null) return movieResult.err;

    Movie movie = movieResult.value;

    Result<List<Review>> result = ReviewRepository.getReviewsByMovieId(ctx.request.get("id_filme").asInt());
    if(result.err != null) return result.err;

    List<Review> reviews = result.value;

    movie.reviews = reviews;

    return new Success(movie).toJson();
  }

  private class Success extends JsonSerializable {
    final public String status = "200";

    final public String mensagem = "Operação executada com sucesso!";

    public Movie filme;

    Success(Movie movie) {
      this.filme = movie;
    }
  }
}
