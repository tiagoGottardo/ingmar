import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

class LoginDTO {
  @JsonIgnore
  public String operacao;
  public String usuario;
  public String senha;
}
