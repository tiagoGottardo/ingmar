import java.util.Map;
import java.util.Scanner;

public class Main { 
  public static void main(String args[]) {
    TcpHandler tcpHandler = new TcpHandler();

    Map<String, Command> commands = Map.of(
        "registrar", new Register(tcpHandler),
        "login",     new Login(tcpHandler),
        "logout",    new Logout(tcpHandler),
        "deletar",   new Delete(tcpHandler),
        "atualizar", new Update(tcpHandler),
        "eu",        new Me(tcpHandler)
    );

    System.out.println("Bem-vindo ao Ingmar Client! (Digite 'ajuda' para ver a lista de comandos)");

    String input;
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("> ");
      if (scanner.hasNextLine()) input = scanner.nextLine(); else continue; 
      String strCmd = input.split(" ")[0];

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
