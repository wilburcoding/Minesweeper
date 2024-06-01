package com.wilburcoding.minesweeper;

public class MinesweeperCell {
    public MinesweeperState state;
    public int countMines;
    public MinesweeperCell() {
        state = MinesweeperState.HIDDEN;
        countMines = 0;
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
}
