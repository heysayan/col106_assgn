import java.util.ArrayList;

public class test {
    public static void main(String[] args) {
        Othello game;
        try{
            game = new Othello("C:/Users/mohds/Desktop/a6/Q2/input.txt");
            System.out.println(game.valid_moves(game.board, 1));
            System.out.println("BestMove(10) : "+game.bestMove(4));
            ArrayList<Integer> seq = game.fullGame(4);System.out.println("game progress: "+seq);System.out.println("winner: "+game.winner);
            print_board(game.board);
        }
        catch(Exception en){en.printStackTrace();}
    }
    public static void print_board(int[][] board){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                System.out.print(board[i][j]+" ");
            }System.out.println();
        }
    }
}
