import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;

class ShowMovies extends Command {
  ShowMovies(TcpHandler tcpHandler) {
    super(tcpHandler);
  }

  protected String run(String args[], String token) {
    if(args.length != 1) return "Você não usou o comando 'listar-filmes' da forma correta\nUse o comando 'ajuda'";

    Request request = new Request(token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    Movie[] movies = Movie.fromJsonArray(response.get("filmes"));

    System.out.println(String.format("| %-20s | %-20s | %-4s | %-15s | %-50s |", "Título", "Diretor", "Ano", "Gênero", "Sinopse"));
    System.out.println("-".repeat(125));

    for(Movie movie : movies) System.out.println(movie.toString());

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
