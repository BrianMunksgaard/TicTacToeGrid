package org.example.tictactoe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brianmunksgaard on 26/02/2018.
 */

public class Board {

    /*
	 * The Tic Tac Toe board.
	 */
    private List<BoardField> boardFields = new ArrayList<>();

    /*
	 * Whether or not the board is empty.
	 */
    //private boolean isGameBoardEmpty;

    /*
     * The next item to put on the board.
     */
    private TicTacToeFieldState nextItem = null;

    /*
     * The number of items on the board.
     */
    private int itemsOnGameBoard;

    /*
     * The total number of moves made.
     */
    private int moves;

    public Board(int[] boardIDs) {
        for( BoardField b : boardFields) {
            b.setState(TicTacToeFieldState.EMPTY);
        }
    }

    public void clearBoard() {
        for( BoardField b : boardFields) {
            b.setState(TicTacToeFieldState.EMPTY);
        }
    }
}
