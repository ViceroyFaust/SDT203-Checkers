package core;

import ui.TerminalUI;

public class Main {
    private Game game;
    private TerminalUI ui;

    /**
     * Parses the string input into two usable coordinates. The format should be something like 3a-4b
     * @param input the input string
     * @return two coordinates, one for source and the other for destination
     */
    private Coordinate[] parseInput(String input) {
        String[] parts = input.split("-");
        Coordinate[] toReturn = new Coordinate[2];

        int row, col;
        String str;
        for (int i = 0; i < 2; ++i) {
            str = parts[i];
            row = 8 - Integer.parseInt(str.substring(0, 1));
            switch(str.charAt(1)) {
                case 'a':
                    col = 0;
                    break;
                case 'b':
                    col = 1;
                    break;
                case 'c':
                    col = 2;
                    break;
                case 'd':
                    col = 3;
                    break;
                case 'e':
                    col = 4;
                    break;
                case 'f':
                    col = 5;
                    break;
                case 'g':
                    col = 6;
                    break;
                case 'h':
                    col = 7;
                    break;
                default:
                    col = 0;
            }
            toReturn[i] = new Coordinate(col, row);
        }

        return toReturn;
    }

    /**
     * Initializes the class
     */
    public Main() {
        game = new Game();
        ui = new TerminalUI();
    }

    /**
     * Represents the primary game loop
     */
    public void run() {
        // Print the game board
        ui.printBoard(game.getBoard());
        // Print game start message
        ui.printGameBegin(game.getPlayer(game.getCurrentPlayer()));
        String input = ui.promptMove();
        Coordinate coordinates[] = parseInput(input);
        game.move(coordinates[0], coordinates[1]);
        while(!game.isGameOver()) {
            ui.printBoard(game.getBoard());
            ui.printPlayerTurn(game.getPlayer(game.getCurrentPlayer()));
            input = ui.promptMove();
            coordinates = parseInput(input);
            game.move(coordinates[0], coordinates[1]);
        }
        ui.printWin(game.getPlayer((game.getCurrentPlayer() + 1) % 2));
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }
}