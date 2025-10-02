import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.application.Platform;

import javafx.fxml.Initializable;
import java.util.ResourceBundle;
import java.net.URL;

import javafx.scene.text.*;
import javafx.scene.paint.Color;

public class MainController implements Initializable {
  @FXML
  private TextFlow mainTextFlow;

  public void appendLog(String s) {
    Platform.runLater(() -> {
      mainTextFlow.getChildren().addAll(formatText(s), new Text("\n"));
    });
  }

  private Text formatText(String s) {
    Text text = new Text(s);
    text.setFont(Font.font("Bitstream Vera Sans Mono", FontWeight.NORMAL, 13));
    return text;
  }

  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    appendLog("Log started!");
    System.out.println("MainController initiazed!");
  }
}
