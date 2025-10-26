import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class Me extends Operation {
  @Override
  public String run(Context ctx) {
    return new Success(ctx.user.name).toJson();
  }

  private class Success extends JsonSerializable {
    final public String status = "200";
    final public String message = "Sucesso: operação realizada com sucesso";
    public String user;

    Success(String user) {
      this.user = user;
    }
  }
}
