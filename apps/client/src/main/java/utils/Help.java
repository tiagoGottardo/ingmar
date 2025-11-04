class Help {
  static public void show() {
    System.out.println("Lista de comandos do usuário:\n");
    System.out.println(" -- Sessão -- ");
    System.out.println("conectar                                                        | Conecta-se ao servidor padrão 0.0.0.0:8080");
    System.out.println("conectar <endereço> <porta>                                     | Conecta-se a um servidor");
    System.out.println("desconectar                                                     | Desconecta do servidor");
    System.out.println("registrar <nome> <senha>                                        | Cria um novo usuário");
    System.out.println("login <nome> <senha>                                            | Entrar no sistema");
    System.out.println("logout                                                          | Saí do sistema");
    System.out.println("sair                                                            | Fecha o cliente\n");

    System.out.println(" -- Conta -- ");
    System.out.println("eu                                                              | Mostra o usuário logado");
    System.out.println("atualizar-senha <senha>                                         | Atualiza senha da conta");
    System.out.println("deletar-conta                                                   | Deleta a conta logada\n");

    System.out.println(" -- Filmes -- ");
    System.out.println("listar-filmes                                                   | Mostra a lista de filmes\n\n");

    System.out.println("Lista de comandos do administrador:\n");

    System.out.println(" -- Usuários -- ");
    System.out.println("listar-usuarios                                                 | Mostra a lista de usuários ");
    System.out.println("atualizar-usuario <id> <senha>                                  | ");
    System.out.println("deletar-usuario <id>                                            | \n");

    System.out.println(" -- Filmes -- ");
    System.out.println("registrar-filme <titulo> <diretor> <ano> <genero> <sinopse>     | ");
    System.out.println("atualizar-filme <id> <titulo> <diretor> <ano> <genero> <sinopse>| ");
    System.out.println("deletar-filme <id>                                              | ");
  }
}
