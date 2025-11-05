import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
class Review {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public int id;

  @JsonProperty("id_filme")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public int movie_id;

  @JsonProperty("titulo")
  public String title;

  @JsonProperty("nome_usuario")
  public String username;

  @JsonProperty("nota")
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public int rating;

  @JsonProperty("descricao")
  public String description;

  @JsonIgnore
  static public Review fromJson(JsonNode json) {
    try {
      return (new ObjectMapper()).treeToValue(json, Review.class);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      System.exit(1);
      return null;
    }
  }

  @JsonIgnore
  public void updateReview(Review other) {
    if(other.id != 0) this.id = other.id;
    if(other.movie_id != 0) this.movie_id = other.movie_id;
    if(other.title != null) this.title = other.title;
    if(other.username != null) this.username = other.username;
    if(other.rating != 0) this.rating = other.rating;
    if(other.description != null) this.description = other.description;
  }

}
