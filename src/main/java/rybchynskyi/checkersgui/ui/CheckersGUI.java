package rybchynskyi.checkersgui.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import rybchynskyi.checkersgui.core.*;

/**
 * The main class of the JavaFX version of the Checkers application. As of 2024-05-05 houses the GUI logic, GUI input
 * processing, and other logic, for I have no experience with MVC.
 * @author Danylo Rybchynskyi
 * @version 2024-05-05
 */
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

    private final Alert CLIorGUIAlert = new Alert(Alert.AlertType.CONFIRMATION,
            "Would you like to play with the GUI?", ButtonType.YES, ButtonType.NO);
    private final Alert computerOrPlayerAlert = new Alert(Alert.AlertType.CONFIRMATION,
            "Would you like to play against a Human or the Computer?",
            new ButtonType("Human"), new ButtonType("Computer"));
    private final Alert invalidMoveAlert = new Alert(Alert.AlertType.ERROR, "Error: Illegal move!", ButtonType.OK);
    private final Alert gameOverAlert = new Alert(Alert.AlertType.CONFIRMATION);

    private final Label moveStatusLabel = new Label(CHOOSE_PIECE_STATUS);
    private final Label playerStatusLabel = new Label(PLAYER_BLACK_STATUS);

    private final CheckersLogic game = new CheckersLogic();
    private CheckersComputerPlayer computerPlayer = null;
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

    private void checkGameOver() {
        if (game.isGameOver()) {
            gameOverAlert.setTitle("Game Over!");
            gameOverAlert.setContentText(game.getNextPlayerString().equals("X") ? BLACK_WIN : WHITE_WIN);
            gameOverAlert.showAndWait();
            Platform.exit();
            System.exit(0);
        }
    }

    private int rowFromId(int id) {
        return id / BUTTON_COLS;
    }

    private int colFromId(int id) {
        return id % BUTTON_COLS;
    }

    private int getButtonId(ActionEvent event) {
        Button button = (Button) event.getSource();
        return Integer.parseInt(button.getId());
    }

    private Coordinate coordinateFromId(int id) {
        return new Coordinate(rowFromId(id), colFromId(id));
    }

    private boolean isCheckerSelected() {
        return selectedChecker != null;
    }

    private void resetSelectedChecker() {
        selectedChecker = null;
    }

    private void setStatusLabelChoosePiece() {
        moveStatusLabel.setText(CHOOSE_PIECE_STATUS);
    }

    private void setStatusLabelChooseDestination() {
        moveStatusLabel.setText(CHOOSE_DESTINATION_STATUS);
    }

    private void updatePlayerStatusLabel() {
        playerStatusLabel.setText((game.getCurrentPlayer() == Player.BLACK) ?
                PLAYER_BLACK_STATUS :
                PLAYER_WHITE_STATUS);
    }

    private boolean isComputerOpponent() {
        return computerPlayer != null;
    }

    private boolean isComputerOpponentTurn() {
        return game.getCurrentPlayer() == Player.WHITE;
    }

    private void handleCheckerButtons(ActionEvent event) {
        int id = getButtonId(event);
        Coordinate currentSelection = coordinateFromId(id);
        if (isCheckerSelected()) {
            Move move = new Move(selectedChecker, currentSelection);
            resetSelectedChecker();
            try {
                game.move(move);
            } catch(IllegalArgumentException e) {
                System.out.println("Illegal move!");
                invalidMoveAlert.showAndWait();
            }
            updateButtons();
            checkGameOver();
            setStatusLabelChoosePiece();
            updatePlayerStatusLabel();
            while (isComputerOpponent() && isComputerOpponentTurn()) {
                move = computerPlayer.calculateMove();
                game.move(move);
                updateButtons();
                checkGameOver();
                setStatusLabelChoosePiece();
                updatePlayerStatusLabel();
            }
        } else {
            selectedChecker = currentSelection;
            setStatusLabelChooseDestination();
        }
    }

    /**
     * Starts the GUI application
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) {
        CLIorGUIAlert.showAndWait();
        if (CLIorGUIAlert.getResult() == ButtonType.NO) {
            RunnerCLI cli = new RunnerCLI();
            cli.run();
            return;
        }
        computerOrPlayerAlert.showAndWait();
        if (computerOrPlayerAlert.getResult().getText().equals("Computer")) {
            System.out.println(computerOrPlayerAlert.getResult().getText());
            computerPlayer = new CheckersComputerPlayer(game);
        }
        initCheckerButtons();
        updateButtons();
        initButtonGridPane();
        initRoot();

        stage.setTitle("Checkers");
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Used as a fallback to launch the JavaFX application
     * @param args the command-line arguments (currently unused)
     */
    public static void main(String[] args) {
        launch();
    }

}