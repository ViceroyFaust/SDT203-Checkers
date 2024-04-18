package ui;

import java.util.Scanner;

public class TerminalUI {
    private Scanner in;

    /**
     * Constructs the class and its attributes
     */
    public TerminalUI() {
        in = new Scanner(System.in);
    }

    /**
     * Prints the board state of the game via the board parameter.
     * @param board 8x8 array representing the current state of the game board
     */
    public void printBoard(char[][] board) {
        for (int i = 0; i < board.length; ++i) {
            System.out.print(8 - i + " |");
            for (int j = 0; j < board[0].length; ++j) {
                System.out.printf(" %c |", board[i][j]);
            }
            System.out.println();
        }
        System.out.println("    a   b   c   d   e   f   g   h");
    }

    /**
     * Prints a message telling the user which player's turn it is now
     * @param player the character representing the player
     */
    public void printPlayerTurn(char player) {
        System.out.println("Player " + player + " - your turn.");
    }

    /**
     * Prints a message telling the user that the game has started and which player's turn it is
     * @param player the character representing the player
     */
    public void printGameBegin(char player) {
        System.out.print("Begin Game. ");
        printPlayerTurn(player);
    }

    /**
     * Prints a win message and congratulates the winning player
     * @param player the character representing the player who won
     */
    public void printWin(char player) {
        System.out.println("Player " + player + " Won the Game!");
    }

    /**
     * Prints the given string. To print a newline, you should include the \n escape sequence in the string.
     * @param text String representing the text to be printed
     */
    public void print(String text) {
        System.out.print(text);
    }

    /**
     * Prints a newline in the output
     */
    public void newline() {
        System.out.println();
    }

    /**
     * Prompts the user for input with a given message
     * @param text Prompt message
     * @return input received from user
     */
    public String prompt(String text) {
        System.out.println(text);
        return in.nextLine();
    }

    /**
     * Prompts the user to input the next move
     * @return input received from user
     */
    public String promptMove() {
        String prompt = "Choose a cell position of piece to be moved and the new position (e.g., 3a-4b):";
        return prompt(prompt);
    }
}
