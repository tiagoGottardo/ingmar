class CreateUser implements Operation {
  CreateUser() { }

  @Override
  public String run() {

    System.out.println("I am inside create user class!");

    return "{ \"status\": \"201\" }";
  }
}
