import com.fasterxml.jackson.databind.JsonNode;

public interface Operation {
  String run(JsonNode request);
}
