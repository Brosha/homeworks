package org.example;


/*
    Реализуйте конструктор, который будет принимать лишь цвет фигуры.
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы конь не мог выйти за доску (доска в нашем случае — это двумерный массив размером 8 на 8, напоминаем, что индексы начинаются с 0) и мог ходить только буквой «Г». Также фигура не может сходить в точку, в которой она сейчас находится. Если конь может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше), то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал символ фигуры, в нашем случае конь — это  H.
 */

public class Horse extends ChessPiece {

    public Horse(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) return false;

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);
        if (this.isTeammate(chessPiece)) {
            return false;
        }
        if ((startLine - 2 == endLine && (startColumn - 1 == endColumn || startColumn + 1 == endColumn))
                || (startLine + 2 == endLine && (startColumn - 1 == endColumn || startColumn + 1 == endColumn))
                || (startColumn - 2 == endColumn && (startLine - 1 == endLine || startLine + 1 == endLine))
                || (startColumn + 2 == endColumn && (startLine - 1 == endLine || startLine + 1 == endLine))
        ) {
            if (!isFreePosition) {
                System.out.println("Horse Killed " + chessPiece.getSymbol());
            }
            return true;

        }

        return false;
    }

    @Override
    public String getSymbol() {
        return "H";
    }
}
