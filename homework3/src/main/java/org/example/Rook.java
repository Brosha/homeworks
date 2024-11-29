package org.example;

/*
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы ладья не могла выйти за доску (доска в нашем случае — это двумерный массив размером 8 на 8,
    напоминаем, что индексы начинаются с 0) и мог ходить только по прямой, также фигура не может сходить в точку, в которой она сейчас находится.
    Если ладья может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше), то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал символ фигуры, в нашем случае ладья — R.
 */


public class Rook extends ChessPiece {

    public Rook(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) return false;

        if (startColumn != endColumn && startLine != endLine) return false;

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);
        if (!isFreePosition && this.isTeammate(chessPiece)) {
            return false;
        }


        int start_x_traversal, start_y_traversal, end_x_traversal, end_y_traversal = 0;

        if ((endLine < startLine || endLine > startLine) && endColumn == startColumn) {

            if (endLine > startLine) {
                start_x_traversal = startLine;
                end_x_traversal = endLine;

            } else {
                start_x_traversal = endLine;
                end_x_traversal = startLine;
            }
            for (int i = start_x_traversal + 1; i < end_x_traversal; i++) {
                if (chessBoard.board[i][endColumn] != null) {
                    System.out.println("Traversal failed");
                    return false;
                }
            }
            System.out.println("Vertical Traversal Passed");

        } else {
            if (endColumn > startColumn) {
                start_y_traversal = startColumn;
                end_y_traversal = endColumn;
            } else {
                start_y_traversal = endColumn;
                end_y_traversal = startColumn;
            }

            for (int j = start_y_traversal + 1; j < end_y_traversal; j++) {
                if (chessBoard.board[endLine][j] != null) {
                    System.out.println("Traversal failed");
                    return false;
                }
            }
            System.out.println("Horizontal Traversal Passed");


        }

        if (!isFreePosition) {
            System.out.println("Rook Killed " + chessPiece.getSymbol());
        }

        if(!getCheck())
            setCheck(true);

        return true;
    }

    @Override
    public String getSymbol() {
        return "R";
    }
}
