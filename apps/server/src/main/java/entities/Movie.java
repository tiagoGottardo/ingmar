import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
class Movie {
  public String id;

  @JsonProperty("titulo")
  public String title;

  @JsonProperty("diretor")
  public String director;

  @JsonProperty("ano")
  public String year;

  @JsonProperty("genero")
  public String[] genre;

  @JsonProperty("sinopse")
  public String synopsis;

  @JsonIgnore
  static public Movie fromJson(JsonNode json) {
    try {
      return (new ObjectMapper()).treeToValue(json, Movie.class);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      System.exit(1);
      return null;
    }

  }

  @JsonIgnore
  public void updateMovie(Movie other) {
    if(other.id != null) this.id = other.id;
    if(other.title != null) this.title = other.title;
    if(other.director != null) this.director = other.director;
    if(other.year != null) this.year = other.year;
    if(other.genre != null) this.genre = other.genre;
    if(other.synopsis != null) this.synopsis = other.synopsis;
  }

}
