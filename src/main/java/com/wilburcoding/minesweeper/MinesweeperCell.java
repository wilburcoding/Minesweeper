package com.wilburcoding.minesweeper;

public class MinesweeperCell {
    public MinesweeperState state;
    public MinesweeperCell() {
        state = MinesweeperState.HIDDEN;
    }

    public MinesweeperState getState() {
        return state;
    }
    public void setState(MinesweeperState state) {
        this.state = state;
    }

}
