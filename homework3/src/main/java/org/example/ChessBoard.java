package org.example;

public class ChessBoard {
    public ChessPiece[][] board = new ChessPiece[8][8]; // creating a field for game
    String nowPlayer;

    public ChessBoard(String nowPlayer) {
        this.nowPlayer = nowPlayer;
    }

    public String nowPlayerColor() {
        return this.nowPlayer;
    }

    public boolean moveToPosition(int startLine, int startColumn, int endLine, int endColumn) {
        if (checkPos(startLine) && checkPos(startColumn)) {

            if (!nowPlayer.equals(board[startLine][startColumn].getColor())) return false;

            if (board[startLine][startColumn].canMoveToPosition(this, startLine, startColumn, endLine, endColumn)) {
                board[endLine][endColumn] = board[startLine][startColumn]; // if piece can move, we moved a piece
                board[startLine][startColumn] = null; // set null to previous cell
                this.nowPlayer = this.nowPlayerColor().equals("White") ? "Black" : "White";

                return true;
            } else return false;
        } else return false;
    }

    public void printBoard() {  //print board in console
        System.out.println("Turn " + nowPlayer);
        System.out.println();
        System.out.println("Player 2(Black)");
        System.out.println();
        System.out.println("\t0\t1\t2\t3\t4\t5\t6\t7");

        for (int i = 7; i > -1; i--) {
            System.out.print(i + "\t");
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(".." + "\t");
                } else {
                    System.out.print(board[i][j].getSymbol() + board[i][j].getColor().substring(0, 1).toLowerCase() + "\t");
                }
            }
            System.out.println();
            System.out.println();
        }
        System.out.println("Player 1(White)");
    }

    public boolean checkPos(int pos) {
        return pos >= 0 && pos <= 7;
    }

    //TODO
    public boolean castling0() {
        if (board[0][4] == null || !board[0][4].getSymbol().equals("K") ||
                board[0][4].getSymbol().equals("K") && !board[0][4].getCheck())
            return false;
        else if (board[0][0] != null && board[0][0].getCheck()
                && !((King) board[0][4]).isUnderAttack(this, 0, 2) ) {
            if (isLineFree(0, 1, 4)) {
                board[0][2] = board[0][4];
                board[0][4] = null;
                board[0][3] = board[0][0];
                board[0][0] = null;
                board[0][2].setCheck(false);
                board[0][3].setCheck(false);
                return true;
            }

        } else if (board[0][7] != null && board[0][7].getCheck()
                && !((King) board[0][4]).isUnderAttack(this, 0, 6)) {
            if (isLineFree(0, 5, 6)) {
                board[0][6] = board[0][4];
                board[0][4] = null;
                board[0][5] = board[0][0];
                board[0][0] = null;
                board[0][6].setCheck(false);
                board[0][5].setCheck(false);
                return true;
            }
        }

        return false;
    }

    //TODO
    public boolean castling7() {

        if (board[7][4] == null || !board[7][4].getSymbol().equals("K") ||
                board[7][4].getSymbol().equals("K") && !board[7][4].getCheck())
            return false;
        else if (board[7][0] != null && board[7][0].getCheck()
                && !((King) board[7][4]).isUnderAttack(this, 7, 2) ) {
            if (isLineFree(7, 1, 4)) {
                board[7][2] = board[7][4];
                board[7][4] = null;
                board[7][3] = board[7][0];
                board[7][0] = null;
                board[7][2].setCheck(false);
                board[7][3].setCheck(false);
                return true;
            }

        } else if (board[7][7] != null && board[7][7].getCheck()
                && !((King) board[7][4]).isUnderAttack(this, 7, 6)) {
            if (isLineFree(7, 5, 6)) {
                board[7][6] = board[7][4];
                board[7][4] = null;
                board[7][5] = board[7][0];
                board[7][0] = null;
                board[7][6].setCheck(false);
                board[7][5].setCheck(false);
                return true;
            }
        }

        return false;


    }

    private boolean isLineFree(int line, int startColumn, int endColumn) {
        for (int j = startColumn; j < endColumn; j++) {
            if (board[line][j] != null) return false;
        }

        return true;
    }
}

