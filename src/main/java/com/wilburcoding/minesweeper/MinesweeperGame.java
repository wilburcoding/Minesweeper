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
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new MinesweeperCell();
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++){
                board[i+9][j+9].setState(MinesweeperState.FOUND);
            }
        }
        for (int i = 0; i < 2;i++) {
            for (int j = 0; j < board.length; j++) {
                for (int k = 0; k < board[i].length; k++) {
                    if (board[j][k].getState() == MinesweeperState.FOUND) {
                        for (int l = 0; l < 3;l++) {
                            for (int m = 0; m < 3;m++) {
                                if (Math.random() < 1/(Math.hypot(9.5-j, 9.5-k)+3.5)) {
                                    board[j+1-l][k+1-m].setState(MinesweeperState.FOUND);
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i =0; i < board.length; i++){
            for (int j =0; j < board[i].length; j++){
                if (board[i][j].getState() == MinesweeperState.HIDDEN){
                    if (Math.random() < 0.19) {
                        board[i][j].setMine(true);
                    }
                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].isMine()) {
                    int count = 0;
                    for (int l = 0; l < 3;l++) {
                        for (int m = 0; m < 3;m++) {
                            int xcoord = i+1-l;
                            int ycoord = j+1-m;
                            if (xcoord > 19 || xcoord < 0 || ycoord > 19 || ycoord < 0){
                                continue;
                            }
                            if (board[xcoord][ycoord].isMine()) {
                                count++;
                            }
                        }
                    }
                    board[i][j].setCountMines(count);
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

}
