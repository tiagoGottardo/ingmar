import com.fasterxml.jackson.databind.JsonNode;

class Context {
  public JsonNode request;
  public User user;

  Context(JsonNode request) {
    this.request = request;
    this.user = null;
  }

  Context(JsonNode request, User user) {
    this.request = request;
    this.user = user;
  }
}
