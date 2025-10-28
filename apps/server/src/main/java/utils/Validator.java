class Validator {
  static public String validateName(String name) {
    if (name.length() < 3 || name.length() > 20)
      return "O nome deve ter entre 3 e 20 caracteres.";

    return null;
  }

  static public String validatePassword(String password) {
    if (password.length() < 3 || password.length() > 20)
      return "A senha deve ter entre 3 e 20 caracteres.";

    return null;
  }
}
