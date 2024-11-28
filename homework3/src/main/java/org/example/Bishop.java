package org.example;

/*
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы слон не мог выйти за доску (доска в нашем случае — это двумерный массив размером 8 на 8,
    напоминаем, что индексы начинаются с 0) и мог ходить только по диагонали, также фигура не может сходить в точку, в которой она сейчас находится.
    Если слон может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше), то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал символ фигуры, в нашем случае слон —  B.
 */
public class Bishop extends ChessPiece {

    public Bishop(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) {
            System.out.println("Is NOT ValidPositions");
            return false;
        }

        if (Math.abs(startLine - endLine) != Math.abs(startColumn - endColumn)) {
            return false;
        }

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);
        System.out.println("boolean isFreePosition = " + isFreePosition);
        System.out.println("isTeammate = " + this.isTeammate(chessPiece));
        if (!isFreePosition && this.isTeammate(chessPiece)) {
            System.out.println("isTeammate");
            return false;
        } else System.out.println("Is NOT A Teammate");

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
            int i = start_x_traversal+1;
            int j = start_y_traversal+1;
            while (i < end_x_traversal && j < end_y_traversal) {
                if (chessBoard.board[i][j] != null) {
                    System.out.println("Traversal failed");
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

            int i = start_x_traversal+1;
            int j = start_y_traversal-1;
            while (i < end_x_traversal && j > end_y_traversal) {
                if (chessBoard.board[i][j] != null) {
                    System.out.println("Traversal failed");
                    return false;
                }
                i++;
                j--;
            }

        }
        if (!isFreePosition) {
            System.out.println("Bishop Killed " + chessPiece.getSymbol());
        }

        return true;
    }

    @Override
    public String getSymbol() {
        return "B";
    }
}
