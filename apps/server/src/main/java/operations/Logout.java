import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class Logout implements Operation {

  @Override
  public String run(JsonNode request) {
    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";
  }
}
