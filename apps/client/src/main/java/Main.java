// import java.io.BufferedReader;
// import java.io.InputStreamReader;
// import java.io.PrintWriter;
// import java.net.ServerSocket;
// import java.net.Socket;


// import javafx.application.Application;
// import javafx.scene.Scene;
// import javafx.stage.Stage;
// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.JsonNode;
// import javafx.scene.layout.StackPane;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import javafx.scene.layout.VBox;
// import com.networknt.schema.ValidationMessage;
// import java.util.Set;

// import javafx.scene.web.WebEngine;
// import javafx.scene.web.WebView;

// import java.io.IOException;

import java.util.Map;
import java.util.Scanner;

public class Main { // extends Application {

  // @Override
  // public void start(Stage stage) throws IOException {

    // WebView webView = new WebView();
    // WebEngine webEngine = webView.getEngine();
    // webEngine.load(getClass().getResource("web/index.html").toExternalForm());

    // StackPane root = new StackPane();
    // root.getChildren().add(webView);
    // Scene scene = new Scene(root);

    // stage.setScene(scene);
    // stage.setTitle("Ingmar");
    // stage.setFullScreen(true);
    // stage.show();

  // }

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

    System.out.println("Bem-vindo ao Ingmar Client!");

    String input;
    Scanner scanner = new Scanner(System.in);

    while (true) {
      System.out.print("> ");
      if (scanner.hasNextLine()) input = scanner.nextLine(); else continue; 
      String strCmd = input.split(" ")[0];

      if (strCmd.equals("conectar")) {
        String err = tcpHandler.validateAndConnect(input);
        if(err != null) System.out.println(err);
        continue;
      }

      if (strCmd.equals("disconectar")) {
        if(tcpHandler.isConnected()) {
          tcpHandler.close();
          System.out.println("Disconectado");
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


