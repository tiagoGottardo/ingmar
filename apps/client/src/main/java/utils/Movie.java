import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Scanner;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
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
  static public Movie[] fromJsonArray(JsonNode json) {
    try {
      return (new ObjectMapper()).treeToValue(json, Movie[].class);
    } catch (JsonProcessingException e) {
      System.out.println(e.getMessage());
      return null;
    }

  }

  private String san(String input, int len) {
    if(input.length() > len) 
      return input.substring(0, input.length() - input.length() + len - 3) + "...";

    return String.format("%-" + len + "s", input);
  }

  @JsonIgnore
  public String toString() {
    String genres = "";
    for(String genre : this.genre) genres += (genre + ", ");
    if(genres.length() > 0) genres = genres.substring(0, genres.length() - 2);

    return "| " +
      san(this.title, 20) + " | " +
      san(this.director, 20) + " | " +
      san(this.year, 4) + " | " + 
      san(genres, 15) + " | " +
      san(this.synopsis, 50) + " |";
  }

  public static Movie createMovieFromTerminal(Scanner scanner) {
    Movie movie = new Movie();

    System.out.print("Título: ");
    movie.title = scanner.nextLine();

    System.out.print("Diretor: ");
    movie.director = scanner.nextLine();

    System.out.print("Ano: ");
    movie.year = scanner.nextLine();

    System.out.print("Digite o(s) Gênero(s) (use ', ' para separar): ");
    movie.genre = (scanner.nextLine()).split(", ");

    System.out.print("Digite a Sinopse: ");
    movie.synopsis = scanner.nextLine();

    return movie;
  }

}
