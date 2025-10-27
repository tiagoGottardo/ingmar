import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

abstract class Command {
  // protected boolean isAuth() { return true; } 
  
  abstract protected boolean validateInput(String args[]);

  abstract protected String run(String args[], TcpHandler tcpHandler);

  protected JsonNode mapJson(String rawJson) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      return objectMapper.readTree(rawJson); 
    } catch (IOException e) {
      return null;
    }
  }

  public String start(String input, TcpHandler tcpHandler) {
    String[] args = input.split(" ");

    if(!validateInput(args)) return "You didn't use the command correctly\nCheck 'help' command";
    
    return run(args, tcpHandler);
  }

}
