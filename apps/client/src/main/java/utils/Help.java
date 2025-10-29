class Help {
  static public void show() {
    System.out.println("Lista de comandos:");
    System.out.println("conectar                    | Conecta-se ao servidor padrão 0.0.0.0:8080");
    System.out.println("conectar <endereço> <porta> | Conecta-se a um servidor");
    System.out.println("desconectar                 | Desconecta do servidor");
    System.out.println("sair                        | Fecha o cliente");
    System.out.println("registrar <nome> <senha>    | Cria um novo usuário");
    System.out.println("login <nome> <senha>        | Entrar no sistema");
    System.out.println("logout                      | Saí do sistema");
    System.out.println("deletar                     | Deleta a conta logada");
    System.out.println("atualizar nome <nome>       | Atualiza nome da conta");
    System.out.println("atualizar senha <senha>     | Atualiza senha da conta");
    System.out.println("eu                          | Mostra o usuário logado");
    System.out.println("ajuda                       | Mostra a lista de comandos");
  }
}
