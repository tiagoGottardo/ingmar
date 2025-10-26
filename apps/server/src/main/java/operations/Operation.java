import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;

abstract class Operation {
  protected boolean isAuth() { return true; } 

  abstract protected String run(Context ctx);

  public String start(JsonNode request) {
    if (!this.isAuth()) return run(new Context(request));

    Claims claims = JWTUtil.validateToken(request.get("token").asText());
    if (!claims) return Failed.response(400, "Invalid Token!");

    String userId = claims.get("id", String.class);

    Result<User> result = UserRepository.getUserById(userId);
    if(result.err != null) return result.err;

    User user = result.value;

    return run(new Context(request, user));
  }

}
