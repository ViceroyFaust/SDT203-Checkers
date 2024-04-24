package core;

import ui.CheckersTextConsole;

public class Main {
    private CheckersLogic game;
    private CheckersTextConsole ui;

    /**
     * Initializes the class
     */
    public Main() {
        game = new CheckersLogic();
        ui = new CheckersTextConsole();
    }

    private void printGameBoard() {
        ui.print(game.getBoardString());
    }

    private void printGameBegin() {
        ui.printGameBegin();
    }

    private void printPlayerTurn() {
        ui.printPlayerTurn(game.getCurrentPlayerString());
    }

    private boolean isExit(String input) {
        return input.equals("exit");
    }

    private boolean isValidMoveString(String input) {
        return Move.isStringValid(input);
    }

    private boolean isValidMove(Move move) {
        return game.isMoveValid(move);
    }

    private Move getMove() {
        String input;
        Move move;
        while(true) {
            input = ui.promptMove();
            if (!isValidMoveString(input)) {
                ui.print("ERROR: Invalid Input. Try Again.\n");
                continue;
            }
            move = Move.parseMove(input);
            if (!isValidMove(move)) {
                ui.print("ERROR: Invalid Move. Try Again.\n");
                continue;
            }
            return move;
        }
    }

    /**
     * Represents the primary game loop
     */
    public void run() {
        boolean firstLoop = true;
        Move move;
        while(!game.isGameOver()) {
            printGameBoard();
            if (firstLoop) {
                printGameBegin();
                firstLoop = false;
            }
            printPlayerTurn();
            move = getMove();
            game.move(move);
        }
        ui.printWin(game.getNextPlayerString());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}