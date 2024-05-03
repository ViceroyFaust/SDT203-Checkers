package rybchynskyi.checkersgui.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import rybchynskyi.checkersgui.core.CheckersLogic;
import rybchynskyi.checkersgui.core.Coordinate;
import rybchynskyi.checkersgui.core.Move;

public class CheckersGUI extends Application {
    private final double ROOT_PADDING = 12;
    private final double BUTTON_PADDING = 1;
    private final int BUTTON_ROWS = 8;
    private final int BUTTON_COLS = 8;
    private final Button[][] checkerButtons = new Button[BUTTON_ROWS][BUTTON_COLS];
    private final GridPane checkerButtonGridPane = new GridPane();
    private final GridPane root = new GridPane();

    private final String PLAYER_WHITE_STATUS = "Player O's turn";
    private final String PLAYER_BLACK_STATUS = "Player X's turn";

    private final String BLACK_WIN = "Player X won!";
    private final String WHITE_WIN = "Player O won!";

    private final String CHOOSE_PIECE_STATUS = "Please choose a checker";
    private final String CHOOSE_DESTINATION_STATUS = "Please make a move";

    private Label moveStatusLabel = new Label(CHOOSE_PIECE_STATUS);
    private Label playerStatusLabel = new Label(PLAYER_BLACK_STATUS);

    private final Alert invalidMoveAlert = new Alert(Alert.AlertType.ERROR, "Error: Illegal move!", ButtonType.OK);
    private final Alert gameOverAlert = new Alert(Alert.AlertType.CONFIRMATION);

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
                toAdd.setOnAction(this::handleCheckerButtons);
                checkerButtons[i][j] = toAdd;
            }
        }
    }

    private void updateButtons() {
        for (int i = 0; i < BUTTON_ROWS; ++i) {
            for (int j = 0; j < BUTTON_COLS; ++j) {
                checkerButtons[i][j].setText(game.getSymbol(i, j));
            }
        }
    }

    private void initRoot() {
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(ROOT_PADDING));
        root.setVgap(ROOT_PADDING);
        root.setHgap(ROOT_PADDING);

        root.add(playerStatusLabel, 0, 0);
        root.add(checkerButtonGridPane, 0, 1);
        root.add(moveStatusLabel, 0, 2);
    }

    private void handleCheckerButtons(ActionEvent event) {
        Button button = (Button) event.getSource();
        int id = Integer.parseInt(button.getId());
        int row = id / BUTTON_COLS;
        int col = id % BUTTON_COLS;
        Coordinate currentSelection = new Coordinate(row, col);
        System.out.println("Clicked row " + row + ", col " + col);
        if (selectedChecker != null) {
            Move move = new Move(selectedChecker, currentSelection);
            selectedChecker = null;
            try {
                game.move(move);
            } catch(IllegalArgumentException e) {
                System.out.println("Illegal move!");
                invalidMoveAlert.showAndWait();
            }
            updateButtons();
            if (game.isGameOver()) {
                gameOverAlert.setTitle("Game Over!");
                gameOverAlert.setContentText(game.getNextPlayerString().equals("X") ? BLACK_WIN : WHITE_WIN);
                gameOverAlert.showAndWait();
                Platform.exit();
                System.exit(0);
            }
            moveStatusLabel.setText(CHOOSE_PIECE_STATUS);
            playerStatusLabel.setText((game.getCurrentPlayerString().equals("X")) ? PLAYER_BLACK_STATUS : PLAYER_WHITE_STATUS);
        } else {
            selectedChecker = currentSelection;
            moveStatusLabel.setText(CHOOSE_DESTINATION_STATUS);
        }
    }

    @Override
    public void start(Stage stage) {
        initCheckerButtons();
        updateButtons();
        initButtonGridPane();
        initRoot();

        stage.setTitle("Checkers");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}