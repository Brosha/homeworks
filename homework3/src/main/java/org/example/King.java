package org.example;

/*
    Реализуйте конструктор, который будет принимать лишь цвет фигуры.
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы фигуры не могли выйти за доску (доска в нашем случае — это двумерный массив размером 8 на 8,
    напоминаем, что индексы начинаются с 0) и могли ходить так, как ходят в шахматах (Королева ходит и по диагонали и по прямой,
    Король — в любое поле вокруг себя), также фигура не может сходить в точку, в которой она сейчас находится.
    Если фигура может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше), то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал строку — символ фигуры, для короля — K, для ферзя — Q.

    Отдельно в классе King создайте метод isUnderAttack(ChessBoard board, int line, int column), возвращающий логическое (boolean) значение,
    который будет проверять, находится ли поле, на котором стоит король (или куда собирается пойти) под атакой.
    Если это так, то метод должен вернуть true, иначе — false. Это позволит нам проверять шахи.

*/

public class King extends ChessPiece {

    public King(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) {
            return false;
        }

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);
        if (this.isTeammate(chessPiece)) {
            return false;
        }
        if ((startLine + 1 == endLine && (startColumn - 1 == endLine || startColumn == endLine || startColumn + 1 == endColumn))
                || (startLine - 1 == endLine && (startColumn - 1 == endLine || startColumn == endLine || startColumn + 1 == endColumn))
                || (startLine == endLine && (startColumn - 1 == endLine || startColumn + 1 == endColumn))
        ) {
            if (isUnderAttack(chessBoard, endLine, endColumn)) return false;
            if (!isFreePosition) {
                System.out.println("King Killed " + chessPiece.getSymbol());
            }
            if (getCheck()) setCheck(false);

            return true;
        }


        return false;
    }

    public boolean isUnderAttack(ChessBoard board, int line, int column) {

        if (!board.checkPos(line) || !board.checkPos(column)) {
            System.out.println("WRONG!");
            return false;
        }

        return (isUnderDiagonalAttack(board, line, column))
                || isUnderLineAttack(board, line, column)
                || isUnderHorseAttack(board, line, column);
    }

    @Override
    public String getSymbol() {
        return "K";
    }

    private boolean isUnderLineAttack(ChessBoard board, int line, int column) {

        if (line + 1 < 8) {
            if (isKing(board, line + 1, column)) {
                return true;
            }
        }
        if (line - 1 >= 0) {
            if (isKing(board, line - 1, column)) {
                return true;
            }
        }
        if (column + 1 < 8) {
            if (isKing(board, line, column + 1)) {
                return true;
            }
        }
        if (column - 1 >= 0) {
            if (isKing(board, line, column - 1)) {
                return true;
            }
        }


        for (int i = line + 1; i < 8; i++) {
            if (board.board[i][column] != null) {
                if (isTeammate(board.board[i][column])) break;
                else {
                    if (isRookOrQueen(board.board[i][column])) return true;
                }
            }
        }
        for (int i = line + 1; i > 0; i--) {
            if (board.board[i][column] != null) {
                if (isTeammate(board.board[i][column])) break;
                else {
                    if (isRookOrQueen(board.board[i][column])) return true;
                }
            }
        }
        for (int i = line - 1; i > 0; i--) {
            if (board.board[i][column] != null) {
                if (isTeammate(board.board[i][column])) break;
                else {
                    if (isRookOrQueen(board.board[i][column])) return true;
                }
            }
        }
        for (int j = column + 1; j < 8; j++) {
            if (board.board[line][j] != null) {
                if (isTeammate(board.board[line][j])) break;
                else {
                    if (isRookOrQueen(board.board[line][j])) return true;
                }
            }
        }

        for (int j = column - 1; j > 0; j--) {
            if (board.board[line][j] != null) {
                if (isTeammate(board.board[line][j])) break;
                else {
                    if (isRookOrQueen(board.board[line][j])) return true;
                }
            }
        }


        return false;
    }

    private boolean isUnderDiagonalAttack(ChessBoard board, int line, int column) {

        if (line + 1 < 8 && column + 1 < 8) {
            if (isKingOrPawn(board, line + 1, column + 1)) {
                return true;
            }
        }
        if (line + 1 < 8 && column - 1 >= 0) {
            if (isKingOrPawn(board, line + 1, column - 1)) {
                return true;
            }
        }
        if (line - 1 >= 0 && column + 1 < 8) {
            if (isKingOrPawn(board, line - 1, column + 1)) {
                return true;
            }
        }
        if (line - 1 > 0 && column - 1 >= 0) {
            if (isKingOrPawn(board, line - 1, column - 1)) {
                return true;
            }
        }
        int i = line + 1;
        int j = column + 1;
        while (i < 8 && j < 8) {
            if (board.board[i][j] != null) {
                if (isTeammate(board.board[i][j])) break;
                else {
                    if (isBishopOrQueen(board.board[i][j])) return true;
                }
            }
            i++;
            j++;
        }

        i = line - 1;
        j = column - 1;
        while (i >= 0 && j >= 0) {
            if (board.board[i][j] != null) {
                if (isTeammate(board.board[i][j])) break;
                else {
                    if (isBishopOrQueen(board.board[i][j])) return true;
                }
            }
            i--;
            j--;
        }

        i = line - 1;
        j = column + 1;
        while (i >= 0 && j < 8) {
            if (board.board[i][j] != null) {
                if (isTeammate(board.board[i][j])) break;
                else {
                    if (isBishopOrQueen(board.board[i][j])) return true;
                }
            }
            i--;
            j++;
        }

        i = line + 1;
        j = column - 1;
        while (i < 8 && j >= 0) {
            if (board.board[i][j] != null) {
                if (isTeammate(board.board[i][j])) break;
                else {
                    if (isBishopOrQueen(board.board[i][j])) return true;
                }
            }
            i++;
            j--;
        }

        return false;
    }

    private boolean isUnderHorseAttack(ChessBoard board, int line, int column) {

        if (board.checkPos(line + 1) && board.checkPos(column + 2)) {
            ChessPiece chessPiece = board.board[line + 1][column + 2];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line + 1) && board.checkPos(column - 2)) {
            ChessPiece chessPiece = board.board[line + 1][column - 2];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line + 2) && board.checkPos(column + 1)) {
            ChessPiece chessPiece = board.board[line + 2][column + 1];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line + 2) && board.checkPos(column - 1)) {
            ChessPiece chessPiece = board.board[line + 2][column - 1];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line - 1) && board.checkPos(column + 2)) {
            ChessPiece chessPiece = board.board[line - 1][column + 2];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line - 1) && board.checkPos(column - 2)) {
            ChessPiece chessPiece = board.board[line - 1][column - 2];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line - 2) && board.checkPos(column + 1)) {
            ChessPiece chessPiece = board.board[line - 2][column + 1];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        if (board.checkPos(line - 2) && board.checkPos(column - 1)) {
            ChessPiece chessPiece = board.board[line - 2][column - 1];
            if (chessPiece != null) {
                if (this.isEnemy(chessPiece) && chessPiece.getSymbol().equals("H"))
                    return true;
            }
        }

        return false;
    }


    private boolean isRookOrQueen(ChessPiece chessPiece) {
        return chessPiece.getSymbol().equals("R") || chessPiece.getSymbol().equals("Q");
    }

    private boolean isBishopOrQueen(ChessPiece chessPiece) {
        return chessPiece.getSymbol().equals("B") || chessPiece.getSymbol().equals("Q");
    }

    private boolean isKing(ChessBoard board, int line, int column) {
        ChessPiece chessPiece = board.board[line][column];
        if (chessPiece == null) return false;
        if (this.isTeammate(chessPiece)) return false;
        return chessPiece.getSymbol().equals("K");

    }

    private boolean isKingOrPawn(ChessBoard board, int line, int column) {
        ChessPiece chessPiece = board.board[line][column];
        if (chessPiece == null) return false;
        if (this.isTeammate(chessPiece)) return false;
        return chessPiece.getSymbol().equals("K") || chessPiece.getSymbol().equals("P");

    }


}
