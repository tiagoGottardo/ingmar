import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

class GetAllUsers extends Operation {
  @Override 
  protected boolean mustBeAdmin() { return true; } 

  @Override
  public String run(Context ctx) {
    Result<List<User>> result = UserRepository.getUsers();
    if(result.err != null) return result.err;

    List<User> users = result.value;

    return new Success(users).toJson();
  }

  private class Success extends JsonSerializable {
    final public String status = "200";

    final public String mensagem = "Operação executada com sucesso!";

    public List<User> usuarios;

    Success(List<User> users) {
      this.usuarios = users;
    }
  }
}
