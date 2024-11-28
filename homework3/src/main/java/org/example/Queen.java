package org.example;

/*
    Реализуйте конструктор, который будет принимать лишь цвет фигуры.
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы фигуры не могли выйти за доску (доска в нашем случае — это двумерный массив размером 8 на 8,
    напоминаем, что индексы начинаются с 0) и могли ходить так, как ходят в шахматах (Королева ходит и по диагонали и по прямой,
    Король — в любое поле вокруг себя), также фигура не может сходить в точку, в которой она сейчас находится.
    Если фигура может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше), то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал строку — символ фигуры, для короля — K, для ферзя — Q.
 */

public class Queen extends ChessPiece {
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) {
            return false;
        }

        if (!((Math.abs(startLine - endLine) == Math.abs(startColumn - endColumn))
                || (startColumn == endColumn || startLine == endLine))) {
            return false;
        }

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);

        if (!isFreePosition && this.isTeammate(chessPiece)) {
            return false;
        }

        if (Math.abs(startLine - endLine) == Math.abs(startColumn - endColumn)) {
            if (!moveLikeBishop(chessBoard, startLine, startColumn, endLine, endColumn))
                return false;
        } else {
            if (!moveLikeRook(chessBoard, startLine, startColumn, endLine, endColumn))
                return false;
        }

        if (!isFreePosition) {
            System.out.println("Queen Killed " + chessPiece.getSymbol());
        }

        return true;
    }

    private boolean moveLikeBishop(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        int start_x_traversal, start_y_traversal, end_x_traversal, end_y_traversal = 0;

        if (endLine < startLine && endColumn < startColumn || endLine > startLine && endColumn > startColumn) {

            if (endLine > startLine) {
                start_x_traversal = startLine;
                start_y_traversal = startColumn;
                end_x_traversal = endLine;
                end_y_traversal = endColumn;
            } else {
                start_x_traversal = endLine;
                start_y_traversal = endColumn;
                end_x_traversal = startLine;
                end_y_traversal = startColumn;

            }

            int i = start_x_traversal + 1;
            int j = start_y_traversal + 1;
            while (i < end_x_traversal && j < end_y_traversal) {
                if (chessBoard.board[i][j] != null) {
                    System.out.println("Traversal Like Bishop failed");
                    return false;
                }
                i++;
                j++;
            }

        } else {
            if (endLine > startLine) {
                start_x_traversal = startLine;
                start_y_traversal = startColumn;
                end_x_traversal = endLine;
                end_y_traversal = endColumn;
            } else {
                start_x_traversal = endLine;
                start_y_traversal = endColumn;
                end_x_traversal = startLine;
                end_y_traversal = startColumn;
            }

            int i = start_x_traversal + 1;
            int j = start_y_traversal - 1;
            while (i < end_x_traversal && j > end_y_traversal) {
                if (chessBoard.board[i][j] != null) {
                    System.out.println("Traversal Like Bishop failed");
                    return false;
                }
                i++;
                j--;
            }

        }
        return true;
    }

    private boolean moveLikeRook(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

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
                    System.out.println("Traversal Like Rook failed");
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
                    System.out.println("Traversal like Rook failed");
                    return false;
                }
            }

            System.out.println("Horizontal Traversal Passed");

        }

        return true;
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}
