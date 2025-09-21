import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("window.fxml"));

    Scene scene = new Scene(fxmlLoader.load(), 800, 600);

    stage.setTitle("Ingmar");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
    int port = 3001;

    try {
      ServerSocket serverSocket = new ServerSocket(port);
      System.out.println("Server is listening on port " + port);

      Socket clientSocket = serverSocket.accept();
      System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

      
      PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

      BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

      out.println("Hello, you are connected to the Java TCP server. Send a message.");

      String clientMessage;
      while ((clientMessage = in.readLine()) != null) {
        System.out.println("Received from client: " + clientMessage);
        
        out.println("Server received: " + clientMessage);

        if ("exit".equalsIgnoreCase(clientMessage.trim())) {
            System.out.println("Client requested to close the connection.");
            break;
        }
      }

      in.close();
      out.close();
      clientSocket.close();
      serverSocket.close();
      System.out.println("Connection closed.");

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

