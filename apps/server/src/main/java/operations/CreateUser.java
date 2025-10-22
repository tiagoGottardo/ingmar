import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

class CreateUser implements Operation {
  @Override
  public String run(String rawJson) {
    System.out.println(rawJson);

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      CreateUserDTO req = objectMapper.readValue(rawJson, CreateUserDTO.class);

    } catch (IOException e) {
      e.printStackTrace();
      return "{ \"status\": \"400\" }";
    }

    return "{ \"status\": \"201\" }";
  }
}
