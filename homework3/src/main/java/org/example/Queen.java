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

public class Queen extends ChessPiece{
    public Queen(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {
        return false;
    }

    public boolean moveLikeBishop (ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {

        return true;
    }

    public boolean moveLikeRook (ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn){
        return true;
    }

    @Override
    public String getSymbol() {
        return "Q";
    }
}
