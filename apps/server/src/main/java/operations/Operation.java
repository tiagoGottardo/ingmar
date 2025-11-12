import com.fasterxml.jackson.databind.JsonNode;
import io.jsonwebtoken.Claims;

abstract class Operation {
  protected boolean isAuth() { return true; } 

  protected boolean mustBeAdmin() { return false; } 

  abstract protected String run(Context ctx);

  public String start(JsonNode request) {
    String err = JsonValidator.validate(request, request.get("operacao").asText());
    if(err != null) return err;

    if (!this.isAuth()) return run(new Context(request));

    if (request.get("token") == null) return Failed.response(401, "Token Inválido!");

    Claims claims = JWTUtil.validateToken(request.get("token").asText());
    if (claims == null) return Failed.response(401, "Token Inválido!");

    Result<User> result = UserRepository.getUserById(claims.get("id", Integer.class));
    if(result.err != null) return result.err;

    User user = result.value;

    if(user.token == null) return Failed.response(401, "Token Inválido!");

    if(mustBeAdmin() && !user.isAdmin()) return Failed.response(403);

    return run(new Context(request, user));
  }

}
