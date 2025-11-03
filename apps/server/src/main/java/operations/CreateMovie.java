import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.mindrot.jbcrypt.BCrypt;

class CreateMovie extends Operation {
  @Override
  protected boolean mustBeAdmin() { return true; } 

  @Override
  public String run(Context ctx) {
    Movie movie = Movie.fromJson(ctx.request.get("filme"));

    String err = MovieRepository.createMovie(movie);
    if(err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "201";

    @JsonProperty
    final private String mensagem = "Filme criado com sucesso!";
  }
}
