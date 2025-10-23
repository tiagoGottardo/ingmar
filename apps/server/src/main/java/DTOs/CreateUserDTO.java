import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

class CreateUserDTO {
  @JsonIgnore
  public String operacao;
  public UserDTO usuario;
}
