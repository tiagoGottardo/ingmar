import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.scene.layout.StackPane;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.layout.VBox;
import com.networknt.schema.ValidationMessage;
import java.util.Set;

import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.IOException;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws IOException {

    WebView webView = new WebView();
    WebEngine webEngine = webView.getEngine();
    webEngine.load(getClass().getResource("web/index.html").toExternalForm());

    StackPane root = new StackPane();
    root.getChildren().add(webView);
    Scene scene = new Scene(root);

    stage.setScene(scene);
    stage.setTitle("Ingmar");
    stage.setFullScreen(true);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }
}
