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

import java.util.HashMap;
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

    HashMap<String, Command> commands = new HashMap<>();
    commands.put("registrar", new Register(tcpHandler));
    commands.put("login", new Login(tcpHandler));
    commands.put("logout", new Logout(tcpHandler));
    commands.put("deletar", new Delete(tcpHandler));
    commands.put("atualizar", new Update(tcpHandler));
    commands.put("eu", new Me(tcpHandler));

    System.out.println("Welcome to Ingmar Client!");

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
        System.out.println("Command '" + strCmd + "' is invalid command.\nType 'help' to see all commands.");
        continue;
      }

      System.out.println(cmd.start(input));

    }
    
    scanner.close();

    System.out.println("You exit Ingmar Client.");
  }
}


