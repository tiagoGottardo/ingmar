import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
class User {
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public int id;

  @JsonProperty("nome")
  public String name;

  public String password;
  public String token;

  User(int id, String name, String password, String token) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.token = token;
  }

  User(int id, String name) {
    this.id = id;
    this.name = name;
  }

  @JsonIgnore
  public boolean isAdmin() {
    return id == 1;
  }
}
