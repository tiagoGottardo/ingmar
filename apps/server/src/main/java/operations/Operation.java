import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;

abstract class Operation {
  private boolean authentication = true;

  abstract protected String run(Context ctx);

  public String start(JsonNode request) {
    if (!this.authentication) return run(new Context(request));

    Claims claims = JWTUtil.validateToken(request.get("token").asText());

    String userId = claims.get("id", String.class);

    Result<User> result = UserRepository.getUserById(userId);
    if(result.err != null) return result.err;

    User user = result.value;

    return run(new Context(request, user));
  }

}
