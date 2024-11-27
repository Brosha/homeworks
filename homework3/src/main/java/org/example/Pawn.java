package org.example;

/*
    Реализуйте конструктор, который будет принимать цвет фигуры.
    Реализуйте метод getColor() так, чтобы он возвращал цвет фигуры.
    Реализуйте метод canMoveToPosition() так, чтобы пешка не могла выйти за доску и могла ходить только вперед.
    Помните, что первый ход пешка может сдвинуться на 2 поля вперед, сделать это можно, например, сравнив координаты.
    То есть, если пешка белая (color.equals("White")) и находится в line == 1, то она может пойти на 2 поля вперед, иначе — нет,
    аналогично с черными пешками. Также фигура не может сходить в точку, в которой она сейчас находится.
    Если пешка может пройти от точки (line, column) до точки (toLine, toColumn) по всем правилам (указанным выше),
     то функция вернет true, иначе — false.
    Реализуйте метод getSymbol так, чтобы он возвращал символ фигуры, в нашем случае пешка — это P.
 */
public class Pawn extends ChessPiece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        if (!isValidPositions(startLine, startColumn, endLine, endColumn)) return false;

        ChessPiece chessPiece = chessBoard.board[endLine][endColumn];
        boolean isFreePosition = (chessPiece == null);
        if (!isFreePosition && this.isTeammate(chessPiece)) {
            return false;
        }

        boolean isWhite = this.getColor().equals("White");
        if ((isWhite && startLine == 1 && startLine + 2 == endLine && startColumn == endColumn && isFreePosition)
                || (!isWhite && startLine == 6 && startLine - 2 == endLine && startColumn == endColumn && isFreePosition)
                || (isWhite && startLine + 1 == endLine && startColumn == startColumn && isFreePosition)
                || (!isWhite && startLine - 1 == endLine && startColumn == startColumn && isFreePosition))
            return true;

        if (((isWhite && startLine + 1 == endLine && (startColumn - 1 == endColumn || startColumn + 1 == endColumn))
                || (!isWhite && startLine -1 == endLine && (startColumn - 1 == endColumn || startColumn + 1 == endColumn)))
                && this.isEnemy(chessPiece)) {
            System.out.println("Pawn Killed " + chessPiece.getSymbol());
            return true;
        }
        return false;
    }

    @Override
    public String getSymbol() {
        return "P";
    }
}
