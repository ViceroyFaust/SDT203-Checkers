package ui;

import java.util.Scanner;

public class CheckersTextConsole {
    private final String PLAYER_TURN_ANNOUNCEMENT = "Player %s - your turn.\n";
    private final String GAME_BEGIN_ANNOUNCEMENT = "Begin game. ";
    private final String GAME_OVER_ANNOUNCEMENT = "Player %s has Won the Game!\n";
    private final String PROMPT_MOVE_MESSAGE = "Choose a cell position of piece to be moved and the new position (e.g., 3a-4b):\n";
    private final String PROMPT_PVP_PVE = "Enter ‘P’ if you want to play against another player; enter ‘C’ to play against computer.\n";
    private final String ERROR_INPUT = "ERROR: Invalid Input. Try Again.\n";
    private final String ERROR_MOVE = "ERROR: Invalid Move. Try Again.\n";
    private final Scanner in;

    /**
     * Constructs the class and its attributes
     */
    public CheckersTextConsole() {
        in = new Scanner(System.in);
    }

    /**
     * Prints a message telling the user which player's turn it is now
     * @param playerName String representing the player
     */
    public void printPlayerTurn(String playerName) {
        System.out.printf(PLAYER_TURN_ANNOUNCEMENT, playerName);
    }

    /**
     * Prints that the game has begun
     */
    public void printGameBegin() {
        System.out.print(GAME_BEGIN_ANNOUNCEMENT);
    }

    /**
     * Prints a win message and congratulates the winning player
     * @param playerName String representing the player
     */
    public void printWin(String playerName) {
        System.out.printf(GAME_OVER_ANNOUNCEMENT, playerName);
    }

    /**
     * Prints the given string. To print a newline, you should include the \n escape sequence in the string.
     * @param text String representing the text to be printed
     */
    public void print(String text) {
        System.out.print(text);
    }

    /**
     * Notifies the user that they made an invalid input
     */
    public void printInputError() {
        print(ERROR_INPUT);
    }

    /**
     * Notifies the user that they have made an invalid move
     */
    public void printMoveError() {
        print(ERROR_MOVE);
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
        System.out.print(text);
        return in.nextLine();
    }

    /**
     * Prompts the user to input the next move
     * @return input received from user
     */
    public String promptMove() {
        return prompt(PROMPT_MOVE_MESSAGE);
    }

    /**
     * Prompts the user whether they want to play against another human or the computer
     * @return input received from the user
     */
    public String promptPVP() {
        return prompt(PROMPT_PVP_PVE);
    }
}
