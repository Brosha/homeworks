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
    который  будет проверять, находится ли поле, на котором стоит король (или куда собирается пойти) под атакой.
    Если это так, то метод должен вернуть true, иначе — false. Это позволит нам проверять шахи.

*/

public class King extends ChessPiece{

    public King(String color) {
        super(color);
    }

    @Override
    public boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn) {
        return false;
    }

    @Override
    public String getSymbol() {
        return "K";
    }
}
