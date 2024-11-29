package org.example;


/*
    строковая переменная color — цвет;
    логическая переменная check, по умолчанию true, она понадобится нам сильно позже;
    конструктор, принимающий в себя строковую переменную color.
    И следующие публичные (public) методы:

    getColor(), возвращающий строку — должен вернуть цвет фигуры;
    абстрактный метод canMoveToPosition(), возвращающий логическое (boolean) значение и принимающий в себя параметры ChessBoard chessBoard, int line, int column, int toLine, int toColumn;
    абстрактный метод getSymbol(), возвращающий строку — тип фигуры.

 */


public abstract class ChessPiece {

    private String color;
    private boolean check = true;

    public ChessPiece(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean canMoveToPosition(ChessBoard chessBoard, int startLine, int startColumn, int endLine, int endColumn);

    public abstract String getSymbol();

    public boolean isTeammate(ChessPiece chessPiece) {
        if (chessPiece == null) return false;
        return this.color.equals(chessPiece.getColor());

    }

    public boolean isEnemy(ChessPiece chessPiece){
        if (chessPiece == null) return false;
        return !this.color.equals(chessPiece.getColor());
    }

    public boolean isValidPositions(int startLine, int startColumn, int endLine, int endColumn) {
        //А зачем это надо, если есть в ChessBoard метод checkPos() ??
        if (startLine == endLine && startColumn == endColumn) return false;

        return endLine >= 0 && endLine <= 7 && endColumn >= 0 && endColumn <= 7;
    }

    public void setCheck(boolean check){
        this.check =check;
    }

    public boolean getCheck(){
        return this.check;
    }




}
