import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.Arrays;

class ShowMovies extends Command {
  ShowMovies(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length > 2) return "Você não usou o comando 'listar-filmes' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    Movie[] movies = Movie.fromJsonArray(response.get("filmes"));
    Movie[] moviesToBeShown;

    if (args.length == 1) 
      moviesToBeShown = movies;
    else 
      moviesToBeShown = Arrays.stream(movies)
        .filter(movie -> { for (String genre : movie.genre) if(genre.equals(args[1])) return true; return false; })
        .toArray(Movie[]::new);

    System.out.println(String.format("| %-3s | %-20s | %-20s | %-4s | %-15s | %-50s |", "Id", "Título", "Diretor", "Ano", "Gênero", "Sinopse"));
    System.out.println("-".repeat(131));

    for(Movie movie : moviesToBeShown) System.out.println(movie.toString());

    return "";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "LISTAR_FILMES";

    @JsonProperty
    private String token;

    Request(String token) {
      this.token = token;
    }
  }

}
