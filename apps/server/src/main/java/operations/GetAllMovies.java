import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class GetAllMovies extends Operation {
  @Override
  public String run(Context ctx) {
    Result<List<Movie>> result = MovieRepository.getMovies();
    if(result.err != null) return result.err;

    List<Movie> movies = result.value;

    return new Success(movies).toJson();
  }

  private class Success extends JsonSerializable {
    final public String status = "200";

    final public String mensagem = "Operação executada com sucesso!";

    public List<Movie> filmes;

    Success(List<Movie> movies) {
      this.filmes = movies;
    }
  }
}
