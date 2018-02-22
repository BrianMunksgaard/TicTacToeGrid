package org.example.tictactoe;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

// we implement the onClickListener - so this means there
//will be an onClick method defined for ALL the views later
//in the onClick method
public class MainActivity extends Activity implements OnClickListener {

	//private TicTacToeField[] board = new TicTacToeField[9];

	/*
	 * The Tic Tac Toe board.
	 */
	//TicTacToeField[][] board = new TicTacToeField[3][3];
	Map<Integer, TicTacToeFieldState> gameBoard = new HashMap<>();

	Map<Integer, Integer> viewBoard = new HashMap<>();

	//Map<Integer, Pair<Integer, TicTacToeField>> board = new HashMap<>();

	/*
	 * Whether or not the board is empty.
	 */
	private boolean isGameBoardEmpty;

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


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isGameBoardEmpty = true;
		nextItem = TicTacToeFieldState.X;
		itemsOnGameBoard = 0;

		// Setup listeners for all 9 fields.
		setListener(0, R.id.felt1);
		setListener(1, R.id.felt2);
		setListener(2, R.id.felt3);
		setListener(3, R.id.felt4);
		setListener(4, R.id.felt5);
		setListener(5, R.id.felt6);
		setListener(6, R.id.felt7);
		setListener(7, R.id.felt8);
		setListener(8, R.id.felt9);
	} 
	
	private void setListener(int fieldNumber, int fieldViewId) {
		View field = findViewById(fieldViewId);
		field.setOnClickListener(this);
		gameBoard.put(fieldViewId, TicTacToeFieldState.EMPTY);
		//gameBoard.put(fieldNumber, TicTacToeFieldState.EMPTY);
		//viewBoard.put(fieldViewId, fieldNumber);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onClick(View view) {
		// TODO Here you need to get the ID of the view 
		// being pressed and then if the view is pressed
		// you need to first put a "X", then next time 
		// put a "O" and also make sure that you cannot
		// put a "O" or a "X" if there is already something.

		if (itemsOnGameBoard >= 5) {
			// Check win condition.
		}

		if(itemsOnGameBoard < 6) {
			if(isEmpty(view.getId())) {
				TicTacToeFieldState item = nextItem;
				nextItem = nextItem == TicTacToeFieldState.X ? TicTacToeFieldState.O : TicTacToeFieldState.X;
				gameBoard.replace(view.getId(), item);
				itemsOnGameBoard++;
				draw(view, item);
			}
		}



/*
		if (view.getId()==R.id.felt1)
		{
			ImageView image = (ImageView) view;

			System.out.println("field 1 pressed");

			//TODO something here

			//An example of how to set the image
			//you need to check if the field is empty
			//before setting a new image
			//and also if the turn is X or O
			image.setImageResource(R.drawable.kryds);
			//then you need to update your int[] array also
		}
		*/
	}

	private void draw(View view, TicTacToeFieldState state) {

		ImageView imageView = (ImageView)view;
		switch (state) {
			case X:
				imageView.setImageResource(R.drawable.kryds);
				break;
			case O:
				imageView.setImageResource(R.drawable.bolle);
				break;
			default:
				imageView.setImageResource(R.drawable.blank);
				break;
		}
	}
	/*
	 * Determines whether or not the specified
	 * board field is empty.
	 */
	private boolean isEmpty(int boardPosition) {
		if(gameBoard.containsKey(boardPosition)) {
			TicTacToeFieldState state = gameBoard.get(boardPosition);
			return state == TicTacToeFieldState.EMPTY;
		}
		return false;
	}

	private boolean hasWinner() {
		List<TicTacToeFieldState> gb = new ArrayList<>(gameBoard.values());

		return 	isWinnnerRow(gb, 0, 1, 2) &&
				isWinnnerRow(gb, 3, 4, 5) &&
				isWinnnerRow(gb, 6, 7, 8) &&
				isWinnnerRow(gb, 0, 3, 6) &&
				isWinnnerRow(gb, 1, 4, 7) &&
				isWinnnerRow(gb, 2, 5, 8) &&
				isWinnnerRow(gb, 0, 4, 8) &&
				isWinnnerRow(gb, 2, 4, 6);
	}

	private boolean isWinnnerRow(List<TicTacToeFieldState> board, int x, int y, int z) {
		return isWinnerRow(board.get(x), board.get(y), board.get(z));
	}

	private boolean isWinnerRow(TicTacToeFieldState s1, TicTacToeFieldState s2, TicTacToeFieldState s3) {
		return s1 == s2 && s1 == s3 && s1 != TicTacToeFieldState.EMPTY;
	}

	private boolean isEmpty(View v) {
		return false;
	}

	private boolean isOccupied() {
		return false;
	}

	/*
	private void drawBoard() {
		for(Map.Entry entry : viewBoard.entrySet()) {

			Integer viewId = (Integer)entry.getKey();
			ImageView imageView = findViewById(viewId);
			TicTacToeFieldState state = getBoardFieldState(imageView);

			switch (state) {
				case X:
					imageView.setImageResource(R.drawable.kryds);
					break;
				case O:
					imageView.setImageResource(R.drawable.bolle);
					break;
				default:
					imageView.setImageResource(R.drawable.blank);
					break;
			}

		}
	}
*/
	private Integer getGameBoardPosition(View view) {
		Integer refToFieldId = viewBoard.get(view.getId());
		return refToFieldId;
	}

	private TicTacToeFieldState getGameBoardFieldState(View v) {
		TicTacToeFieldState state = gameBoard.get(getGameBoardPosition(v));
		return state;
	}

	/*
	private boolean isTicTacToeWon(int row, int column, TicTacToePlayer player) {

		// We will use -1 for 'ZERO' and +1 for player 'EX'
		int point = player == TicTacToePlayer.Y ? -1 : 1;

		board[]
		// update row
		stateOfTheGame[row] += point

		// update column
		stateOfTheGame[BOARD_SIZE + column] += point

		// update diagonal1
		if (row == column) {
			stateOfTheGame[2*BOARD_SIZE] += point

			// case (2, 2) in 3 * 3 Board
			let shouldUpdateDia2 = (BOARD_SIZE + 1) / 2 == column ? true : false;

			if ( shouldUpdateDia2 ) {
				stateOfTheGame[2*BOARD_SIZE + 1] += point
			}
		}

		// update diagonal2
		if (row + column == BOARD_SIZE + 1)
			stateOfTheGame[2*BOARD_SIZE + 1] += point

		let i = stateOfTheGame.indexOf(3)
		let j = stateOfTheGame.indexOf(-3)
		// check for the winner in all rows, columns, diagonals
		return (i >= 0 || j >= 0) ? true : false
	}

console.log( isTicTacToeWon(0, 0, 'ZERO') ) // false
			console.log( isTicTacToeWon(0, 1, 'ZERO') ) // false
			console.log( isTicTacToeWon(0, 2, 'ZERO') ) // true


	private int score(int game) {
		if(game == 10) {
			return 10;
		} else if(game == -10) {
			return -10;
		} else {
			return 0;
		}
	}

	private minmax(int game) {

	}
*/
	/*

	def minimax(game)
    return score(game) if game.over?
    scores = [] # an array of scores
    moves = []  # an array of moves

    # Populate the scores array, recursing as needed
    game.get_available_moves.each do |move|
        possible_game = game.get_new_state(move)
        scores.push minimax(possible_game)
        moves.push move
    end

    # Do the min or the max calculation
    if game.active_turn == @player
        # This is the max calculation
        max_score_index = scores.each_with_index.max[1]
        @choice = moves[max_score_index]
        return scores[max_score_index]
    else
        # This is the min calculation
        min_score_index = scores.each_with_index.min[1]
        @choice = moves[min_score_index]
        return scores[min_score_index]
    end
end
	 */
/*
	# @player is the turn taking player
	def score(game)
    if game.win?(@player)
			return 10
	elsif game.win?(@opponent)
			return -10
			else
			return 0
	end
			end
			*/
}
