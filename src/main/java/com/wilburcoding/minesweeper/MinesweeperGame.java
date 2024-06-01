package com.wilburcoding.minesweeper;

public class MinesweeperGame {
    private MinesweeperCell[][] board;
    public MinesweeperGame() {
        board = new MinesweeperCell[20][20];
    }

    public MinesweeperCell[][] getBoard() {
        return board;
    }

    public void generateInitialBoard() {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {

            }
        }
        for (int i = 0; i < 9; i++) {

        }
    }
}
