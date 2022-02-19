package tpel18130.main_code;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private MenuItem Quit;

    @FXML
    private VBox vbox;

    @FXML
    void Exit() {
        Platform.exit();
    }

}
