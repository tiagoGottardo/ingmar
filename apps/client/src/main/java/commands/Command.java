import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

abstract class Command {
  protected TcpHandler tcpHandler;

  Command(TcpHandler tcpHandler) {
    this.tcpHandler = tcpHandler;
  }

  protected boolean isAuth() { return true; } 
  
  abstract protected String run(String args[], String token);

  protected JsonNode mapJson(String rawJson) { ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readTree(rawJson); 
    } catch (IOException e) {
      return null;
    }
  }

  public String start(String input) {
    if(!tcpHandler.isConnected()) return "Você precisa se conectar a algum servidor para executar esse comando.";

    String token = TokenManager.getToken();

    if(isAuth() && token == null) return "Você precisa realizar login para executar esse comando!";

    String[] args = input.split(" ");

    // if(!validateInput(args)) return "Você não executou esse comando corretamente\nVeja o comando 'help' para entender melhor.";
    
    return run(args, token);
  }

}
