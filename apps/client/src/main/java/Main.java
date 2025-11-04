import java.util.Map;
import java.util.Scanner;

public class Main { 
  public static void main(String args[]) {
    TcpHandler tcpHandler = new TcpHandler();
    Scanner scanner = new Scanner(System.in);

    Map<String, Command> commands = Map.ofEntries(
      Map.entry("registrar", new Register(tcpHandler)),
      Map.entry("login", new Login(tcpHandler)),
      Map.entry("logout", new Logout(tcpHandler)),
      Map.entry("deletar", new Delete(tcpHandler)),
      Map.entry("atualizar-senha", new Update(tcpHandler)),
      Map.entry("eu", new Me(tcpHandler)),
      Map.entry("listar-filmes", new ShowMovies(tcpHandler)),
      Map.entry("listar-usuarios", new ShowUsers(tcpHandler)),
      Map.entry("atualizar-usuario", new AdminUpdateUser(tcpHandler)),
      Map.entry("deletar-usuario", new AdminDeleteUser(tcpHandler)),
      Map.entry("registrar-filme", new RegisterMovie(tcpHandler, scanner))
    );

    System.out.println("Bem-vindo ao Ingmar Client! (Digite 'ajuda' para ver a lista de comandos)");

    String input;
    while (true) {
      System.out.print("> ");
      if (scanner.hasNextLine()) input = scanner.nextLine(); else continue; 
      String strCmd = input.split(" ")[0];

      if (strCmd.equals("admin")) {
        tcpHandler.connect("0.0.0.0", 8080);
        System.out.println(commands.get("login").start("login admin admin"));
        continue;
      }

      if (strCmd.equals("user")) {
        tcpHandler.connect("0.0.0.0", 8080);
        System.out.println(commands.get("login").start("login user user"));
        continue;
      }

      if (strCmd.equals("conectar")) {
        if(input.split(" ").length == 1) {
          tcpHandler.connect("0.0.0.0", 8080);
          continue;
        }

        String err = tcpHandler.validateAndConnect(input);
        if(err != null) System.out.println(err);
        continue;
      }

      if (strCmd.equals("desconectar")) {
        if(tcpHandler.isConnected()) {
          tcpHandler.close();
          System.out.println("Desconectado");
        } else System.out.println("Você não está conectado a nenhum servidor");
        
        continue;
      }

      if(strCmd.equals("ajuda")) { Help.show(); continue; }

      if(strCmd.equals("sair")) break;

      Command cmd = commands.get(strCmd);

      if(cmd == null) {
        System.out.println("O comando '" + strCmd + "' não existe.\nUse o comando 'ajuda' para ver todos os comandos.");
        continue;
      }

      System.out.println(cmd.start(input));

    }
    
    scanner.close();

    System.out.println("Você saiu do Ingmar Client.");
  }
}
