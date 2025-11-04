import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.IOException;
import java.util.Scanner;

class RegisterMovie extends Command {
  private Scanner scanner;

  RegisterMovie(TcpHandler tcpHandler, Scanner scanner) {
    super(tcpHandler);
    this.scanner = scanner;
  }

  protected String run(String args[], String token) {
    if(args.length != 1) return "Você não usou o comando 'registrar-filme' da forma correta\nUse o comando 'ajuda'";

    Movie movie = Movie.createMovieFromTerminal(scanner);

    Request request = new Request(movie, token);

    String res = tcpHandler.sendMessage(request.toJson());
    if(res == null) return "O servidor fechou a conexão";

    JsonNode response = mapJson(res); 
    if(response == null) return "Algo deu errado ao desserializar o json de resposta";

    if(Integer.parseInt(response.get("status").asText()) != 201) {
      System.out.println("Algo deu errado.");
      return response.get("mensagem").asText();
    }

    return "Filme registrado com sucesso";
  }

  private class Request extends JsonSerializable {
    @JsonProperty
    final private String operacao = "CRIAR_FILME";

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
