package com.wilburcoding.minesweeper;

import java.util.ArrayList;

public class MinesweeperGame {
    private MinesweeperCell[][] board;
    private int mineCount;
    private int size;

    public MinesweeperGame(int size) {
        board = new MinesweeperCell[size][size];
        mineCount = 0;
        this.size = size;
    }

    public int getMineCount() {
        return mineCount;
    }

    public MinesweeperCell[][] getBoard() {
        return board;
    }
    public String toString() {
        String str = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                str += board[i][j].toString();
            }
            str += "\n";
        }
        return str;

    }

    public void generateInitialBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new MinesweeperCell();
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                board[i + (board.length)/2 - 1][j + (board.length)/2 - 1].setState(MinesweeperState.FOUND);
            }
        }
        ArrayList<String> coordsGen = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < board.length; j++) {
                for (int k = 0; k < board[i].length; k++) {
                    if (board[j][k].getState() == MinesweeperState.FOUND) {
                        for (int l = 0; l < 3; l++) {
                            for (int m = 0; m < 3; m++) {
                                if (Math.random() < 1 / (Math.hypot(9.5 - j, 9.5 - k) + 3.1)) {
                                    board[j + 1 - l][k + 1 - m].setState(MinesweeperState.FOUND);
                                    coordsGen.add((j + 1 - l) + "," + (k + 1 - m));
                                }
                            }
                        }
                    }
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].getState() == MinesweeperState.HIDDEN) {
                    if (Math.random() < 0.19) {
                        board[i][j].setMine(true);
                    }
                }
            }
        }
        for (String item : coordsGen) {
            int x = Integer.parseInt(item.split(",")[0]);
            int y = Integer.parseInt(item.split(",")[1]);
            for (int l = 0; l < 3; l++) {
                for (int m = 0; m < 3; m++) {
                    if (board[x + 1 - l][y + 1 - m].getState() == MinesweeperState.HIDDEN && Math.random() < 0.21) {
                        board[x + 1 - l][y + 1 - m].setMine(true);
                    }

                }
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (!board[i][j].isMine()) {
                    int count = 0;
                    for (int l = 0; l < 3; l++) {
                        for (int m = 0; m < 3; m++) {
                            int xcoord = i + 1 - l;
                            int ycoord = j + 1 - m;
                            if (xcoord > board.length-1 || xcoord < 0 || ycoord > board.length-1 || ycoord < 0) {
                                continue;
                            }
                            if (board[xcoord][ycoord].isMine()) {
                                count++;
                            }
                        }
                    }
                    board[i][j].setCountMines(count);
                    if (count == 0 && coordsGen.contains(i + "," + j)) {
                        for (int l = 0; l < 3; l++) {
                            for (int m = 0; m < 3; m++) {
                                int xcoord = i + 1 - l;
                                int ycoord = j + 1 - m;
                                if (xcoord > board.length-1 || xcoord < 0 || ycoord > board.length-1 || ycoord < 0) {
                                    continue;
                                }
                                if (!board[xcoord][ycoord].isMine()) {
                                    board[xcoord][ycoord].setState(MinesweeperState.FOUND);
                                } else {
                                    board[xcoord][ycoord].setState(MinesweeperState.HIDDEN);
                                }
                            }
                        }
                    }
                } else {
                    mineCount++;
                    board[i][j].setState(MinesweeperState.HIDDEN);

                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].isMine()) {
                    board[i][j].setState(MinesweeperState.HIDDEN);

                }
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void clearArea(int x, int y) {
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                int xCoord = x + 1 - l;
                int yCoord = y + 1 - m;
                if (xCoord > board.length-1 || xCoord < 0 || yCoord > board.length-1 || yCoord < 0) {
                    continue;
                }
                if (board[xCoord][yCoord].getCountMines() == 0 && board[xCoord][yCoord].getState() == MinesweeperState.HIDDEN && !board[xCoord][yCoord].isMine()) {
                    board[xCoord][yCoord].setState(MinesweeperState.FOUND);
                    clearArea(xCoord, yCoord);
                }
                if (!board[xCoord][yCoord].isMine()) {
                    board[xCoord][yCoord].setState(MinesweeperState.FOUND);

                }

            }
        }

    }

    public boolean speedClear(int x, int y) {
        //Jt named this
        int countFlags = 0;
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                int xCoord = x + 1 - l;
                int yCoord = y + 1 - m;
                if (xCoord > board.length-1 || xCoord < 0 || yCoord > board.length-1 || yCoord < 0) {
                    continue;
                }
                if (board[xCoord][yCoord].getState() == MinesweeperState.FLAGGED) {
                    countFlags++;
                }

            }
        }
        if (countFlags != board[x][y].getCountMines()) {
            return false;
        }
        for (int l = 0; l < 3; l++) {
            for (int m = 0; m < 3; m++) {
                int xCoord = x + 1 - l;
                int yCoord = y + 1 - m;
                if (xCoord > board.length-1 || xCoord < 0 || yCoord > board.length-1 || yCoord < 0) {
                    continue;
                }
                if (board[xCoord][yCoord].getState() != MinesweeperState.FLAGGED && board[xCoord][yCoord].isMine()) {
                    return true;
                } else {
                    if (board[xCoord][yCoord].getState() == MinesweeperState.HIDDEN) {
                        if (board[xCoord][yCoord].getCountMines() == 0 && !board[xCoord][yCoord].isMine()) {
                            System.out.println("Clear area");
                            System.out.println(xCoord + "," + yCoord);
                            clearArea(xCoord, yCoord);

                        } else {
                            board[xCoord][yCoord].setState(MinesweeperState.FOUND);
                        }
                    }


                }

            }
        }
        return false;

    }

    public int getMineLeft() {
        int minesLeft = mineCount;
        for (int i = 0; i < board.length;i++) {
            for (int j = 0; j < board[i].length;j++) {
                if (board[i][j].getState() == MinesweeperState.FLAGGED) {
                    minesLeft--;
                }
            }
        }
        return minesLeft;
    }
    public int getSize() {
        return size;
    }
}
