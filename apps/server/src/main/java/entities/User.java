import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
class User {
  public String id;
  @JsonProperty("nome")
  public String name;
  public String password;
  public String token;

  User(String id, String name, String password, String token) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.token = token;
  }

  User(String id, String name) {
    this.id = id;
    this.name = name;
  }

  @JsonIgnore
  public boolean isAdmin() {
    return Integer.parseInt(id) == 1;
  }
}
