import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class UpdateMovie extends Operation {
  @Override
  protected boolean mustBeAdmin() { return true; } 

  @Override
  public String run(Context ctx) {
    Movie newData = Movie.fromJson(ctx.request.get("filme"));

    Result<Movie> result = MovieRepository.getMovieById(newData.id);
    if(result.err != null) return result.err;

    Movie movie = result.value;

    movie.updateMovie(newData);

    String err = MovieRepository.updateMovie(movie); 
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";

    @JsonProperty
    final private String mensagem = "Operação executada com sucesso!";
  }
}
