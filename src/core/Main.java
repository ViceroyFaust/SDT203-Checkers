package core;

import ui.CheckersTextConsole;

/**
 * The main class that is the entrypoint of the program. It handles all of the runner logic.
 * @author Danylo Rybchynskyi
 * @version 2024-04-26
 */
public class Main {
    private CheckersLogic game;
    private CheckersTextConsole ui;
    private CheckersComputerPlayer npc;

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

    private boolean isValidMove(Move move) {
        return game.isMoveValid(move);
    }

    /**
     * Prompts the user whether they want to play against the computer
     * @return true - playing against the computer; false - otherwise
     */
    private boolean promptPVE() {
        String input;
        while(true) {
            input = ui.promptPVP();
            if (input.equalsIgnoreCase("c"))
                return true;
            if (input.equalsIgnoreCase("p"))
                return false;
            ui.printInputError();
        }
    }

    /**
     * Prompts the player for a move, then checks whether it can be parsed and whether the move is valid. If any of the
     * checks fail, the program will notify the user of the error and will prompt for a move again.
     * @return Move parsed from player input
     */
    private Move getMove() {
        String input;
        Move move;
        while(true) {
            input = ui.promptMove();
            try {
                move = Move.parseMove(input);
            } catch(IllegalArgumentException e) {
                ui.printInputError();
                continue;
            }
            if (!isValidMove(move)) {
                ui.printMoveError();
                continue;
            }
            return move;
        }
    }

    private void createNPC() {
        npc = new CheckersComputerPlayer(game);
    }

    /**
     * Represents the primary game loop
     */
    public void run() {
        boolean firstLoop = true;
        boolean npcOpponent = false;
        Move move;
        while(!game.isGameOver()) {
            printGameBoard();
            if (firstLoop) {
                printGameBegin();
                npcOpponent = promptPVE();
                if (npcOpponent) createNPC();
                firstLoop = false;
            }
            printPlayerTurn();
            if (npcOpponent && game.getCurrentPlayer() == Player.WHITE) {
                move = npc.calculateMove();
            } else {
                move = getMove();
            }
            game.move(move);
        }
        ui.printWin(game.getNextPlayerString());
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}