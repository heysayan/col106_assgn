public class test2 {
    public static void main(String[] args) {SnakesLadder game;
        try{
            game = new SnakesLadder("c:\\Users\\mohds\\Desktop\\a5\\Q2\\input.txt");
            System.out.println("Optimal Moves: "+game.OptimalMoves());
            System.out.println(game.Query(54, 50));
            System.out.println(game.Query(54, 95));
            System.out.println(game.Query(54,10));
            System.out.println(game.FindBestNewSnake()[0]+","+game.FindBestNewSnake()[1]);//printer(game.board_BFS);
            //SnakesLadder game2 = new SnakesLadder("c:\\Users\\mohds\\Desktop\\a5\\Q2\\2btc2");
            //System.out.println(game2.Query(2,100));
        }catch(Exception en){en.printStackTrace();}
        

    }
    public static void printer(int[][] board_BFS){
        System.out.println("Printing the board! ");
        for (int i =0;i<board_BFS.length;i++){
            System.out.println(i+" ("+board_BFS[i][0]+","+board_BFS[i][1]+")");
        }
    }
}
