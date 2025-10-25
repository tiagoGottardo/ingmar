import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

class Failed {
  @JsonProperty
  private String status;

  @JsonProperty
  private String message;

  private Failed(int status, String message) {
    this.status = String.valueOf(status);
    this.message = message;
  }

  private Failed(int status) {
    this.status = String.valueOf(status);
    this.message = "Default message"; // #TODO: create the HashMap to this.
  }

  private String toJson() {
    try {
      return (new ObjectMapper()).writeValueAsString(this);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      return Failed.response(500);
    }
  }

  static public String response (int status) {
    return new Failed(status).toJson();
  }

  static public String response (int status, String message) {
    return new Failed(status, message).toJson();
  }

}
