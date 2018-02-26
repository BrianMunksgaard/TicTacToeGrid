package org.example.tictactoe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


// we implement the onClickListener - so this means there
//will be an onClick method defined for ALL the views later
//in the onClick method
public class MainActivity extends Activity implements OnClickListener, View.OnLongClickListener {

	/*
	 * The view IDs of all fields on the board.
	 */
	private int[] viewIDs = new int[] { R.id.felt1, R.id.felt2, R.id.felt3, R.id.felt4, R.id.felt5, R.id.felt6, R.id.felt7, R.id.felt8, R.id.felt9 };

	private Map<Integer, BoardField> gameBoard = new TreeMap<>();
	//private List<BoardField> gameBoard = new ArrayList<>(9);



	//private TicTacToeField[] board = new TicTacToeField[9];

	/*
	 * The Tic Tac Toe board.
	 */
	//List<BoardField> gameBoard = new ArrayList<>();

	//TicTacToeField[][] board = new TicTacToeField[3][3];
	//Map<Integer, TicTacToeFieldState> gameBoard = new HashMap<>();
	//Map<Integer, Integer> viewBoard = new HashMap<>();
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

	private boolean winnerFound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isGameBoardEmpty = true;
		nextItem = TicTacToeFieldState.X;
		itemsOnGameBoard = 0;

		// Initialize the board.
		for(int i = 0; i <= 8 ; i++) {
			int vid = viewIDs[i];

			View v = findViewById(vid);
			v.setOnClickListener(this);
			v.setOnLongClickListener(this);

			gameBoard.put(vid, new BoardField(i, vid, TicTacToeFieldState.EMPTY));
		}

		// Start with en empty board.
		clearBoard();

	} 

	/*
	 * Clear the game board.
	 */
	private void clearBoard() {
		winnerFound = false;
		for(BoardField bf : gameBoard.values()) {
			bf.setState(TicTacToeFieldState.EMPTY);
			draw(bf);
		}
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

		if(isOccupied(view)) {
			Context context = getApplicationContext();
			CharSequence text = "Occupied";
			int duration = Toast.LENGTH_SHORT;
			Toast.makeText(context, text, duration).show();
		}

		if(!winnerFound) {

			if (itemsOnGameBoard < 6) {
				if (isEmpty(view.getId())) {
					TicTacToeFieldState item = nextItem;
					Log.d("KURT", "Putting " + nextItem.toString() + " on the board.");
					nextItem = nextItem == TicTacToeFieldState.X ? TicTacToeFieldState.O : TicTacToeFieldState.X;
					gameBoard.get(view.getId()).setState(item);
					itemsOnGameBoard++;
					draw(view, item);
				}
			}

			if (itemsOnGameBoard >= 5) {
				Log.d("KURT", "About to check winner.");
				if (hasWinner()) {
					Context context = getApplicationContext();
					CharSequence text = "We have a winner";
					int duration = Toast.LENGTH_SHORT;
					Toast.makeText(context, text, duration).show();
				}
				// Check win condition.
			}

			List<BoardField> gb = new ArrayList<>(gameBoard.values());
			for (BoardField bf : gb) {
				Log.d("KURT", bf.getFieldNumber() + " = " + bf.getState().toString());
			}
		}

	}

	@Override
	public boolean onLongClick(View view) {
		Context context = getApplicationContext();
		CharSequence text = "Clicking a long time.";
		int duration = Toast.LENGTH_SHORT;
		Toast.makeText(context, text, duration).show();
		//view.setRotation(0.5f);
		
		return true;
	}

	private void draw(BoardField bf) {
		draw(findViewById(bf.getViewId()), bf.getState());
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
			return gameBoard.get(boardPosition).isEmpty();
		}
		return false;
	}

	private boolean hasWinner() {
		List<BoardField> gb = new ArrayList<>(gameBoard.values());

		return 	isWinnnerRow(gb, 0, 1, 2) ||
				isWinnnerRow(gb, 3, 4, 5) ||
				isWinnnerRow(gb, 6, 7, 8) ||
				isWinnnerRow(gb, 0, 3, 6) ||
				isWinnnerRow(gb, 1, 4, 7) ||
				isWinnnerRow(gb, 2, 5, 8) ||
				isWinnnerRow(gb, 0, 4, 8) ||
				isWinnnerRow(gb, 2, 4, 6);
	}

	private boolean isWinnnerRow(List<BoardField> gb, int x, int y, int z) {
        Log.d( "KURT", "isWinnnerRow: " + x + ", " + y + ", " + z);
        return isWinnerRow(gb.get(x).getState(), gb.get(y).getState(), gb.get(z).getState());
	}

	private boolean isWinnerRow(TicTacToeFieldState s1, TicTacToeFieldState s2, TicTacToeFieldState s3) {
        Log.d( "KURT", "isWinnerRow: s1 = " + s1.toString());
        Log.d( "KURT", "isWinnerRow: s2 = " + s2.toString());
        Log.d( "KURT", "isWinnerRow: s3 = " + s3.toString());
        return s1 == s2 && s1 == s3 && s1 != TicTacToeFieldState.EMPTY;
	}

	private boolean isEmpty(View v) {
		return false;
	}

	private boolean isOccupied(View v) {
		return gameBoard.get(v.getId()).getState() != TicTacToeFieldState.EMPTY;
	}
}
