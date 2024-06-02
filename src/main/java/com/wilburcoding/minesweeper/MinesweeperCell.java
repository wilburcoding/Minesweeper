package com.wilburcoding.minesweeper;

public class MinesweeperCell {
    public MinesweeperState state;
    public int countMines;
    public boolean mine;
    public MinesweeperCell() {
        state = MinesweeperState.HIDDEN;
        countMines = 0;
        mine = false;
    }

    public MinesweeperState getState() {
        return state;
    }
    public void setState(MinesweeperState state) {
        this.state = state;
    }

    public void setCountMines(int countMines) {
        this.countMines = countMines;
    }

    public int getCountMines() {
        return countMines;
    }

    public void setMine(boolean mine) {
        this.mine = mine;
    }

    public boolean isMine() {
        return mine;
    }
    public String toString() {
        return (state==MinesweeperState.HIDDEN ? " " : (state==MinesweeperState.FLAGGED ? "\uD83D\uDEA9" : (countMines > 0 ? "" + countMines : " ")));
    }
}
