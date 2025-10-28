import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;

class DeleteUser extends Operation {
  @Override
  public String run(Context ctx) {
    String err = UserRepository.deleteUserById(ctx.user.id);
    if (err != null) return err;

    return new Success().toJson();
  }

  private class Success extends JsonSerializable {
    @JsonProperty
    final private String status = "200";

    @JsonProperty
    final private String mensagem = "Operação executada com sucesso!";
  }
}
