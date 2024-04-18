package ui;

public class TerminalUI {
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
}
