import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

class JsonSerializable {
    public String toJson() {
      try {
        return (new ObjectMapper()).writeValueAsString(this);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
        return "Falha em uma serialização json.";
      }
    }
}
