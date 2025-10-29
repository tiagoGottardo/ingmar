class Validator {
  static public String validateName(String name) {
    if (name.length() < 3 || name.length() > 20)
      return "O nome deve ter entre 3 e 20 caracteres.";

    if(!isAlphaNumeric(name))
      return "O nome deve conter apenas letras e números.";

    return null;
  }

  static public String validatePassword(String password) {
    if (password.length() < 3 || password.length() > 20)
      return "A senha deve ter entre 3 e 20 caracteres.";

    if(!isAlphaNumeric(password))
      return "A senha deve conter apenas letras e números.";

    return null;
  }

  public static boolean isAlphaNumeric(String str) {
    return str.matches("^[a-zA-Z0-9]*$");
  }
}
