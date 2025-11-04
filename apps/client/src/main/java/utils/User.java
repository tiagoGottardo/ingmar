
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
class User {
  public String id;

  @JsonProperty("nome")
  public String name;

  @JsonIgnore
  static public User[] fromJsonArray(JsonNode json) {
    try {
      return (new ObjectMapper()).treeToValue(json, User[].class);
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
    return "| " + san(this.id, 3) + " | " + san(this.name, 20) + " |";
  }
}
