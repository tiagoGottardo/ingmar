class User {
  public String id;
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

  public boolean isAdmin() {
    return Integer.parseInt(id) == 1;
  }
}
