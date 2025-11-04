import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.Scanner;

class UpdateMovie extends Command {
  private Scanner scanner;

  UpdateMovie(TcpHandler tcpHandler, Scanner scanner) {
    super(tcpHandler);
    this.scanner = scanner;
  }

  protected String run(String args[], String token) {
    if(args.length != 2) return "Você não usou o comando 'atualizar-filme' da forma correta\nUse o comando 'ajuda'";

    Movie movie = Movie.createMovieFromTerminal(scanner);

    movie.id = args[1];

    Request request = new Request(movie, token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 200)
      return response.get("mensagem").asText();

    return "Filme atualizado com sucesso";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "EDITAR_FILME";

    @JsonProperty
    private Movie filme;

    @JsonProperty
    private String token;

    Request(Movie movie, String token) {
      this.filme = movie;
      this.token = token;
    }
  }

}
