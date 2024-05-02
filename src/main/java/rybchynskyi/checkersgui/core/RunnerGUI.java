package rybchynskyi.checkersgui.core;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RunnerGUI {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}