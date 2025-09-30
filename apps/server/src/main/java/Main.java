import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javafx.concurrent.Task;

import javafx.application.Platform;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.ValidationMessage;
import java.util.Set;

import java.io.IOException;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("window.fxml"));
    Parent root = loader.load();

    MainController controller = loader.getController();

    stage.setTitle("Ingmar");
    stage.setFullScreen(true);
    stage.setScene(new Scene(root));
    stage.show();

    Task<Void> serverTask = new Task<>() {
      @Override
      protected Void call() throws Exception {
        serve(controller);
        return null;
      }
    };

    new Thread(serverTask).start();
  }

  public static void main(String[] args) {
    launch(args);
  }
}

