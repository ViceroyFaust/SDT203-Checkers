package rybchynskyi.checkersgui.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import rybchynskyi.checkersgui.core.CheckersLogic;
import rybchynskyi.checkersgui.core.Coordinate;
import rybchynskyi.checkersgui.core.Move;

import javax.swing.*;

public class CheckersGUI extends Application {
    private final double BUTTON_PADDING = 1;
    private final int BUTTON_ROWS = 8;
    private final int BUTTON_COLS = 8;
    private final Button[][] checkerButtons = new Button[BUTTON_ROWS][BUTTON_COLS];
    private final GridPane checkerButtonGridPane = new GridPane();
    private final CheckersLogic game = new CheckersLogic();
    private Coordinate selectedChecker = null;

    private void initButtonGridPane() {
        checkerButtonGridPane.setPadding(new Insets(BUTTON_PADDING));
        checkerButtonGridPane.setHgap(BUTTON_PADDING);
        checkerButtonGridPane.setVgap(BUTTON_PADDING);
        for (int i = 0; i < BUTTON_ROWS; ++i) {
            for (int j = 0; j < BUTTON_COLS; ++j) {
                checkerButtonGridPane.add(checkerButtons[i][j], j, i);
            }
        }
        checkerButtonGridPane.setAlignment(Pos.CENTER);
    }

    private void initCheckerButtons() {
        int buttonId;
        for(int i = 0; i < BUTTON_ROWS; i++) {
            for(int j = 0; j < BUTTON_COLS; j++) {
                buttonId = BUTTON_ROWS * i + j;
                Button toAdd = new Button();
                toAdd.setMinSize(32, 32);
                toAdd.setId(Integer.toString(buttonId));
                toAdd.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        Button button = (Button) event.getSource();
                        int id = Integer.parseInt(button.getId());
                        int row = id / BUTTON_COLS;
                        int col = id % BUTTON_COLS;
                        Coordinate currentSelection = new Coordinate(row, col);
                        System.out.println("Clicked row " + row + ", col " + col);
                        if (selectedChecker != null) {
                            Move move = new Move(selectedChecker, currentSelection);
                            selectedChecker = null;
                            game.move(move);
                            updateButtons();
                        } else {
                            selectedChecker = currentSelection;
                        }
                    }
                });
                checkerButtons[i][j] = toAdd;
            }
        }
    }

    public void updateButtons() {
        for (int i = 0; i < BUTTON_ROWS; ++i) {
            for (int j = 0; j < BUTTON_COLS; ++j) {
                checkerButtons[i][j].setText(game.getSymbol(i, j));
            }
        }
    }

    @Override
    public void start(Stage stage) {
        initCheckerButtons();
        updateButtons();
        initButtonGridPane();

        stage.setTitle("Checkers");
        stage.setScene(new Scene(checkerButtonGridPane));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}