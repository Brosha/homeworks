import org.example.*;

public class Test {
    private static ChessBoard chessBoard = new ChessBoard("White");
    public static void main(String[] args) throws InterruptedException {

        Bishop white_bishop = new Bishop("White");
        Bishop white_bishop2 = new Bishop("White");
        Bishop black_bishop = new Bishop("Black");
        Horse white_horse = new Horse("White");
        Pawn white_pawn = new Pawn("White");
        Pawn black_pawn = new Pawn("Black");
        Rook white_rook = new Rook("White");
        Queen white_quen = new Queen("White");

/*
        System.out.println(white_bishop.getColor());
        System.out.println(white_bishop.getSymbol());
        chessBoard.board[3][3]=white_bishop;
        testMoveToPosition(3,3, 3,3);

        chessBoard.board[3][3]=white_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 6,6);


        chessBoard.board[1][1]=white_bishop;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 3,5);

        chessBoard.board[3][3]=white_bishop;
        chessBoard.board[6][6]=white_bishop2;
        chessBoard.printBoard();
        System.out.println("Output: " + testMoveToPosition(3,3, 6,6));

        chessBoard.board[3][3]=white_bishop;
        chessBoard.board[5][5]= white_bishop2;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 6,6);

        chessBoard.board[3][3]=white_bishop;
        chessBoard.board[7][7] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3,7,7);

        chessBoard.board[3][3] = white_horse;
        chessBoard.board[2][5] = white_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3,2,5);

        chessBoard.board[3][3] = white_horse;
        chessBoard.printBoard();
        testMoveToPosition(3,3,2,5);

        chessBoard.board[3][3] = white_horse;
        chessBoard.board[2][5] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3,2,5);

        chessBoard.board[3][3] = white_pawn;
        chessBoard.printBoard();
        testMoveToPosition(3,3,4,3);

        chessBoard.board[3][3] = black_pawn;
        chessBoard.printBoard();
        testMoveToPosition(3,3,4,3);

        chessBoard.board[1][1] = white_pawn;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 3,1);

        chessBoard.board[1][1] = white_pawn;
        chessBoard.board[3][1] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 3,1);


        chessBoard.board[1][1] = white_pawn;
        chessBoard.board[2][2] = black_pawn;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 2,2);

        chessBoard.board[1][1] = white_pawn;
        chessBoard.board[2][2] = black_pawn;
        chessBoard.printBoard();
        testMoveToPosition(2,2, 1,1);


        chessBoard.board[1][1] = white_rook;
        chessBoard.printBoard();
        testMoveToPosition(1,1,1,7);

        chessBoard.board[1][1] = white_rook;
        chessBoard.printBoard();
        testMoveToPosition(1,1,7,1);


        chessBoard.board[1][1] = white_rook;
        chessBoard.printBoard();
        testMoveToPosition(1,1,2,7);

        chessBoard.board[1][1] = white_rook;
        chessBoard.board[1][4] = black_pawn;
        chessBoard.printBoard();
        testMoveToPosition(1,1,1,7);

        chessBoard.board[1][1] = white_rook;
        chessBoard.board[1][4] = black_pawn;
        chessBoard.printBoard();
        testMoveToPosition(1,1,1,4);


        chessBoard.board[3][3] = white_quen;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 6,6);
        chessBoard.board[1][1] = white_quen;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 6,1);

        chessBoard.board[3][3] = white_quen;
        chessBoard.board[5][5] = white_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 6,6);

        chessBoard.board[3][3] = white_quen;
        chessBoard.board[5][5] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 5,5);

        chessBoard.board[3][3] = white_quen;
        chessBoard.board[3][5] = white_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 3,5);

        chessBoard.board[3][3] = white_quen;
        chessBoard.board[3][5] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(3,3, 3,5);

        chessBoard.board[1][1] = white_quen;
        chessBoard.board[3][6] = black_bishop;
        chessBoard.printBoard();
        testMoveToPosition(1,1, 3,6);
*/

    }
    private static boolean testMoveToPosition(int startLine, int startColumn, int endLine, int endColumn){
        if (chessBoard.moveToPosition(startLine, startColumn, endLine, endColumn)) {
            System.out.println("Успешно передвинулись");
            chessBoard.printBoard();
            wipeBoard();
            return true;
        } else System.out.println("Передвижение не удалось");
        chessBoard.printBoard();
        wipeBoard();
        return false;
    }

    private static void wipeBoard (){
        for (int i=0; i<8; i++){
            for (int j=0; j<8; j++){
                chessBoard.board[i][j]=null;
            }
        }
        System.out.println("*************BOARD WAS WIPPED*************");
    }
    public static ChessBoard buildBoard() {
        ChessBoard board = new ChessBoard("White");

        board.board[0][0] = new Rook("White");
        board.board[0][1] = new Horse("White");
        board.board[0][2] = new Bishop("White");
        board.board[0][3] = new Queen("White");
        board.board[0][4] = new King("White");
        board.board[0][5] = new Bishop("White");
        board.board[0][6] = new Horse("White");
        board.board[0][7] = new Rook("White");
        board.board[1][0] = new Pawn("White");
        board.board[1][1] = new Pawn("White");
        board.board[1][2] = new Pawn("White");
        board.board[1][3] = new Pawn("White");
        board.board[1][4] = new Pawn("White");
        board.board[1][5] = new Pawn("White");
        board.board[1][6] = new Pawn("White");
        board.board[1][7] = new Pawn("White");

        board.board[7][0] = new Rook("Black");
        board.board[7][1] = new Horse("Black");
        board.board[7][2] = new Bishop("Black");
        board.board[7][3] = new Queen("Black");
        board.board[7][4] = new King("Black");
        board.board[7][5] = new Bishop("Black");
        board.board[7][6] = new Horse("Black");
        board.board[7][7] = new Rook("Black");
        board.board[6][0] = new Pawn("Black");
        board.board[6][1] = new Pawn("Black");
        board.board[6][2] = new Pawn("Black");
        board.board[6][3] = new Pawn("Black");
        board.board[6][4] = new Pawn("Black");
        board.board[6][5] = new Pawn("Black");
        board.board[6][6] = new Pawn("Black");
        board.board[6][7] = new Pawn("Black");
        return board;
    }

}
