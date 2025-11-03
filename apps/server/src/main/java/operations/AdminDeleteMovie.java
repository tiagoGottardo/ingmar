import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class AdminDeleteMovie extends Operation {
  @Override
  protected boolean mustBeAdmin() { return true; } 

  @Override
  public String run(Context ctx) {
    String movieId = ctx.request.get("id").asText();

    String err = MovieRepository.deleteMovieById(movieId);
    if (err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";

    @JsonProperty
    final private String mensagem = "Operação executada com sucesso!";
  }
}
