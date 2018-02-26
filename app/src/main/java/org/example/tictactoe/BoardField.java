package org.example.tictactoe;

import android.util.Log;

/**
 * Created by brianmunksgaard on 26/02/2018.
 */

public class BoardField {

    private int fieldNumber;
    private int viewId;
    private TicTacToeFieldState state;

    public BoardField(int fieldNumber, int viewId, TicTacToeFieldState state) {
        this.fieldNumber = fieldNumber;
        this.viewId = viewId;
        this.state = state;
        Log.d("KURT", "New field: " + fieldNumber + ", " + viewId + ", " + state.toString());
    }

    public int getFieldNumber() {
        return fieldNumber;
    }

    public void setFieldNumber(int fieldNumber) {
        this.fieldNumber = fieldNumber;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public TicTacToeFieldState getState() {
        return state;
    }

    public void setState(TicTacToeFieldState state) {
        Log.d("KURT", "Changing state in field " + this.fieldNumber + " from " + this.state.toString() + " to " + state.toString());
        this.state = state;
    }

    public boolean isEmpty() {
        return this.state == TicTacToeFieldState.EMPTY;
    }
}
