import java.io.*;
import java.util.*;

public class Othello {
    int turn;
    int winner;
    int board[][];
    //add required class variables here

    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j){
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        //Student can choose to add preprocessing here
    }

    //add required helper functions here
    private int return_opponent(int player){
        if (player==0) return 1;
        else  return 0;
    }
    private ArrayList<Integer> check_cell_for_valid_move(int cell,int player){
        //checks if the cell is a valid move for player and returns all cells that are to be flipped to player's side(including the given cell)
        int i = cell/8;int j = cell-(i*8);  //i-->row  , j--> column
        int opp = return_opponent(player);  //opponent value
        ArrayList<Integer> move = new ArrayList<Integer>();
        if (board[i][j]==-1){   //(i,j) is empty cell
            int gotit=0;    //flag that tells if a line is found that allows for a move
            if (i>1 && board[i-1][j]==opp){//1 looking at North
                int steps=2; //steps moved above
                while (steps<i && board[i-steps][j]==opp) steps+=1;    //checking all above steps
                if (board[i-steps][j]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+j);steps-=1;}
                }
            }
            if (i>1 && j<6 && board[i-1][j+1]==opp){//2 looking at North-East
                int steps=2; //steps moved above
                while (steps<i && (j+steps<7) && board[i-steps][j+steps]==opp) steps+=1;    //checking all above steps
                if (board[i-steps][j+steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+j+steps);steps-=1;}
                }
            }
            if (j<6 && board[i][j+1]==opp){//3 looking at East
                int steps=2; //steps moved above
                while (j+steps<7 && board[i][j+steps]==opp) steps+=1;    //checking all above steps
                if (board[i][j+steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*i+(j+steps));steps-=1;}
                }
            }
            if (i<6 && j<6 && board[i+1][j+1]==opp){//4 looking at South-East
                int steps=2; //steps moved above
                while (i+steps<7 && j+steps<7 && board[i+steps][j+steps]==opp) steps+=1;    //checking all above steps
                if (board[i+steps][j+steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+(j+steps));steps-=1;}
                }
            }
            if (i<6 && board[i+1][j]==opp){//5 looking at South
                int steps=2; //steps moved above
                while (i+steps<7 && board[i+steps][j]==opp) steps+=1;    //checking all above steps
                if (board[i+steps][j]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+j);steps-=1;}
                }
            }
            if (i<6 && j>1 && board[i+1][j-1]==opp){//6 looking at South-west
                int steps=2; //steps moved above
                while (i+steps<7 && steps<j && board[i+steps][j-steps]==opp) steps+=1;    //checking all above steps
                if (board[i+steps][j-steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+(j-steps));steps-=1;}
                }
            }
            if (j>1 && board[i][j-1]==opp){//7 looking at West
                int steps=2; //steps moved above
                while (steps<j && board[i][j-steps]==opp) steps+=1;    //checking all above steps
                if (board[i][j-steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*i+(j-steps));steps-=1;}
                }
            }
            if (i>1 && j>1 && board[i-1][j-1]==opp){//8 looking at North-West
                int steps=2; //steps moved above
                while (steps<i && steps<j && board[i-steps][j-steps]==opp) steps+=1;    //checking all above steps
                if (board[i-steps][j-steps]==player){     //if there's a last player piece at the end of line
                    gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+(j-steps));steps-=1;}
                }
            }

            if (gotit==1){  //this cell is a valid move
                move.add(8*i+j);
            }
        }
        return move;
    }
    public ArrayList<ArrayList<Integer>> valid_moves(int[][] board,int player){
        //returns the Arraylist of valid moves for the specified player in the specified board
        //the last index of the move arraylist inside moves arraylist is the empty cell and rest are the cells to be flipped
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        //int opp = return_opponent(player);  //opponent
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                if (board[i][j]==-1){   //(i,j) is empty cell
/*
                    ArrayList<Integer> move = new ArrayList<Integer>();
                    int gotit=0;    //flag that tells if a line is found that allows for a move
                    if (i>1 && board[i-1][j]==opp){//1 looking at North
                        int steps=2; //steps moved above
                        while (steps<i && board[i-steps][j]==opp) steps+=1;    //checking all above steps
                        if (board[i-steps][j]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+j);steps-=1;}
                        }
                    }
                    if (i>1 && j<6 && board[i-1][j+1]==opp){//2 looking at North-East
                        int steps=2; //steps moved above
                        while (steps<i && (j+steps<7) && board[i-steps][j+steps]==opp) steps+=1;    //checking all above steps
                        if (board[i-steps][j+steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+j+steps);steps-=1;}
                        }
                    }
                    if (j<6 && board[i][j+1]==opp){//3 looking at East
                        int steps=2; //steps moved above
                        while (j+steps<7 && board[i][j+steps]==opp) steps+=1;    //checking all above steps
                        if (board[i][j+steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*i+(j+steps));steps-=1;}
                        }
                    }
                    if (i<6 && j<6 && board[i+1][j+1]==opp){//4 looking at South-East
                        int steps=2; //steps moved above
                        while (i+steps<7 && j+steps<7 && board[i+steps][j+steps]==opp) steps+=1;    //checking all above steps
                        if (board[i+steps][j+steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+(j+steps));steps-=1;}
                        }
                    }
                    if (i<6 && board[i+1][j]==opp){//5 looking at South
                        int steps=2; //steps moved above
                        while (i+steps<7 && board[i+steps][j]==opp) steps+=1;    //checking all above steps
                        if (board[i+steps][j]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+j);steps-=1;}
                        }
                    }
                    if (i<6 && j>1 && board[i+1][j-1]==opp){//6 looking at South-west
                        int steps=2; //steps moved above
                        while (i+steps<7 && steps<j && board[i+steps][j-steps]==opp) steps+=1;    //checking all above steps
                        if (board[i+steps][j-steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i+steps)+(j-steps));steps-=1;}
                        }
                    }
                    if (j>1 && board[i][j-1]==opp){//7 looking at West
                        int steps=2; //steps moved above
                        while (steps<j && board[i][j-steps]==opp) steps+=1;    //checking all above steps
                        if (board[i][j-steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*i+(j-steps));steps-=1;}
                        }
                    }
                    if (i>1 && j>1 && board[i-1][j-1]==opp){//8 looking at North-West
                        int steps=2; //steps moved above
                        while (steps<i && steps<j && board[i-steps][j-steps]==opp) steps+=1;    //checking all above steps
                        if (board[i-steps][j-steps]==player){     //if there's a last player piece at the end of line
                            gotit=1;steps-=1;while(steps>0){move.add(8*(i-steps)+(j-steps));steps-=1;}
                        }
                    }

                    if (gotit==1){  //this cell is a valid move
                        move.add(8*i+j);moves.add(move);
                    }*/
                    ArrayList<Integer> move = check_cell_for_valid_move((8*i)+j, player);
                    if (move.size()!=0) moves.add(move);
                }
            }
        }
        return moves;
    }
    private void modify_board(ArrayList<Integer> move,int player){
        //changes cells mentioned in move arraylist to player value
        for(int i=0;i<move.size();i++){
            int r = move.get(i)/8;int c = move.get(i)-(r*8);
            this.board[r][c]=player;
        }
    }
    private void revert_board(ArrayList<Integer> move,int player){
        //returns the board to previous state by giving opponent his pieces and move cell is made empty
        //Note: it does not modify the move arraylist
        int opp = return_opponent(player);
        for(int i=0;i<move.size()-1;i++){
            int r = move.get(i)/8;int c = move.get(i)-(r*8);
            this.board[r][c]=opp;
        }
        int empty_cell_row = move.get(move.size()-1)/8;int empty_cell_col = move.get(move.size()-1)-(empty_cell_row*8);
        this.board[empty_cell_row][empty_cell_col]=-1;
    }
    private int[] bestMove_helper(int k, int turn, ArrayList<Integer> alpha_beta/*(list of size1)parent node alpha-beta value used for pruning */){
        //recursively traverses the k-step decision tree and returns the best move as per the value of turn
        //prunes certain branches as per the alpha-beta value of parent passed as arguement
        int[] best_move = {-1,-1};   //best move position(initialised as -1),score
        if (k==1){  //if it's a one-step look-ahead
            ArrayList<ArrayList<Integer>> moves = valid_moves(this.board,turn);  //valid moves for the player who has the current turn in the method call
            //note: assumption that moves are ordered in the arraylist
            int pruned = 0;  //flag that tells if some cases are pruned here
            if (turn==this.turn){//it's a max node
                int score = Integer.MIN_VALUE;
                for (int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //doing the move
                    int new_score = boardScore();   //the utility at this case
                    if (new_score>score){   //if new score is better than old score
                        score = new_score;//alpha_beta.set(alpha_beta.size()-1,score);
                        best_move[0]=move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //taking back the move
                    //pruning forthcoming checks if current score has already exceeded the beta value of parent node
                    if (/*alpha_beta.get(0)!=null &&*/ alpha_beta.get(0)<=score){
                        pruned = 1;break;
                    }
                }
                best_move[1]=score;     //storing the score
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alpha_beta.set(0,score);
            }

            else{   //it's a min node
                int score = Integer.MAX_VALUE;
                for (int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //doing the move
                    int new_score = boardScore();   //the utility at this case
                    if (new_score<score){   //if new score is worser than old score
                        score = new_score;
                        best_move[0]=move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //taking back the move
                    //pruning forthcoming checks if current score has already preceeded the alpha value of parent node
                    if (/*alpha_beta.get(0)!=null &&*/ alpha_beta.get(0)>=score){   //if parent's alpha value is better than current score
                        pruned = 1;break;
                    }
                }
                best_move[1]=score;     //storing the score
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alpha_beta.set(0,score);

            }
        }
        else{   //for a k-step lookahead where k!=1
            ArrayList<ArrayList<Integer>> moves = valid_moves(this.board,turn);  //valid moves for the player who has the current turn in the method call
            //note: assumption that moves are ordered in the arraylist
            int pruned = 0;     //flag that tells if any case is pruned
            if (turn==this.turn){       //it's a max node
                int score = Integer.MIN_VALUE;  //declaring and initialising the best score variable
                int opp = return_opponent(turn);    //opponent value of current value of turn
                ArrayList<Integer> alpha = new ArrayList<Integer>(1);alpha.add(Integer.MIN_VALUE);   //alpha value for this node
                for (int i=0;i<moves.size();i++){   //checking all the possible moves for best move
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //doing the  move
                    int[] new_move;  //declaring the new_move array
                    new_move = bestMove_helper(k-1,opp,alpha);  //recursive call to get the best move in k-1 lookahead after current move
                    //Note: the above calls would update the value of alpha
                    if (new_move[1]>score){ //if new score is better than old score
                        score = new_move[1];//alpha_beta.add(score);
                        best_move[0]=move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //taking back the move
                    //pruning if current score has already exceeded the beta value of parent node
                    if (/*alpha_beta.get(0)!=null &&*/ alpha_beta.get(0)<=alpha.get(0)){
                        pruned=1;break;
                    }
                }
                if(score==Integer.MIN_VALUE) {//System.out.println("no valid move in this max node!");
                    int[] new_move = bestMove_helper(k-1,opp,alpha);score = new_move[1];
                }
                best_move[1]=score;     //storing the score
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alpha_beta.set(0,alpha.get(0));
            }

            else{       //it's a min node
                int score = Integer.MAX_VALUE;  //declaring and initialising the best score variable
                ArrayList<Integer> beta = new ArrayList<Integer>(1);beta.add(Integer.MAX_VALUE);   //beta value for this node
                int opp = return_opponent(turn);    //opponent value of current value of turn
                for (int i=0;i<moves.size();i++){   //checking all the possible moves for best move
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //altering the  board
                    int[] new_move;  //declaring the new_move array
                    new_move =bestMove_helper(k-1,opp,beta);
                    if (new_move[1]<score){ //if new score is better than old score
                        score = new_move[1];
                        best_move[0]=move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //returning the board to previous  state
                    //pruning if current score has already preceeded the alpha value of parent node
                    if (/*alpha_beta.get(0)!=null &&*/ alpha_beta.get(0)>=beta.get(0)){
                        pruned=1;break;
                    }
                }
                if(score==Integer.MAX_VALUE){// System.out.println("no valid move in this min node!");
                    int[] new_move = bestMove_helper(k-1, opp, beta);score = new_move[1];
                }
                best_move[1]=score;     //storing the score
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alpha_beta.set(0,beta.get(0));
            }
        }
        return best_move;
    }

    private int bestMove_helper2(int[] alphabeta,int turn,int k,int halt_length/*length of no moves occurences */){
        //returns the best move at the current node and updates the respective best score for the parent cell
        int best_move = -1;  //declaring and initialising best move as -1
        if (k==1){  //if it's a one-step lookahead
            ArrayList<ArrayList<Integer>> moves = valid_moves(this.board,turn);  //valid moves for the player who has the current turn in the method call
            //note: assumption that moves are ordered in the arraylist
            int pruned = 0;  //flag that tells if some cases are pruned here
            if (turn==this.turn){//it's a max node
                int alpha = Integer.MIN_VALUE;
                for (int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //doing the move
                    int new_score = boardScore();   //the utility at this case
                    if (new_score>alpha){   //if new score is better than old score
                        alpha = new_score;
                        best_move = move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //taking back the move
                    //pruning forthcoming checks if current alpha has already exceeded the beta value of parent node
                    if (alphabeta[0]<=alpha){
                        pruned = 1;break;
                    }
                }
                if (moves.size()==0){
                    halt_length+=1;
                    alpha = boardScore();
                    if (halt_length>=2){
                        if (alpha>0) alpha = Integer.MAX_VALUE;
                        else if(alpha<0) alpha = Integer.MIN_VALUE;
                    }
                    else if (alpha>=alphabeta[0]) pruned=1;  //if the boardscore is better than the parent beta value,prune this case
                }
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alphabeta[0]=alpha;  //setting the worst score for the parent node
            }
            else{//it's a min node
                int beta = Integer.MAX_VALUE;
                for (int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn); //doing the move
                    int new_score = boardScore();   //the utility at this case
                    if (new_score<beta){   //if new score is worser than old score(beta)
                        beta = new_score;//alpha_beta.set(alpha_beta.size()-1,score);
                        best_move = move.get(move.size()-1);
                    }
                    revert_board(move, turn);   //taking back the move
                    //pruning forthcoming checks if current beta has already preceeded the alpha value of parent node
                    if (alphabeta[0]>=beta){    
                        pruned = 1;break;
                    }
                    //NOTE: pruning works even if the it happens at the end of loop
                }
                if(moves.size()==0){    //if no move is possible
                    halt_length+=1;
                    beta = boardScore();
                    if(halt_length>=2){
                        if (beta>0) beta = Integer.MAX_VALUE;
                        else if (beta<0) beta = Integer.MIN_VALUE;
                    }
                    else if (beta<=alphabeta[0]) pruned=1;   //if the boardscore is worser than the parent alpha value,prune this case
                }
                //if nothing is pruned for this leaf node, save the score which is the new min value for parent node
                if (pruned==0) alphabeta[0]=beta;  //setting the best score for the parent node
            }
        }

        else{   //k steps lookahead where k>1
            ArrayList<ArrayList<Integer>> moves = valid_moves(this.board,turn);  //valid moves for the player who has the current turn in the method call
            //note: assumption that moves are ordered in the arraylist
            int pruned = 0;  //flag that tells if some cases are pruned here
            int opp = return_opponent(turn);
            if (turn==this.turn){   //it's a max node
                int[] alpha = {Integer.MIN_VALUE};
                int best_score = Integer.MIN_VALUE;  //to notice if alpha is improved by the new bestmove call
                for(int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn);   //doing the move
                    bestMove_helper2(alpha, opp, k-1,0);  //alpha is uppdated here
                    revert_board(move, turn);   //taking back the move
                    if (best_score!=alpha[0]) {     //alpha is improved by above call, so change best move
                        best_move = move.get(move.size()-1);    //updating the bestmove
                        best_score = alpha[0];
                    }
                    if(alpha[0]>=alphabeta[0]){
                        pruned = 1;break;
                    }
                }
                if (moves.size()==0){   //no move case
                    bestMove_helper2(alpha, opp, k-1,halt_length+1);
                    if(alpha[0]>=alphabeta[0]) pruned=1;    //prune this case since alpha here is still higher than parent beta
                }
                if (pruned==0) alphabeta[0] = alpha[0];     //if the alpha is worser than beta
            }
            else{   //it's a min node
                int[] beta = {Integer.MAX_VALUE};
                int best_score = Integer.MAX_VALUE;  //to notice if alpha is improved by the new bestmove call
                for(int i=0;i<moves.size();i++){
                    ArrayList<Integer> move = moves.get(i);
                    modify_board(move, turn);   //doing the move
                    bestMove_helper2(beta, opp, k-1,0);
                    revert_board(move, turn);   //taking back the move
                    if (best_score!=beta[0]) {     //beta is decreased further by above call, so change best move
                        best_move = move.get(move.size()-1);    //updating the best move
                        best_score = beta[0];
                    }
                    if(beta[0]<=alphabeta[0]){
                        pruned = 1;break;
                    }
                }
                if (moves.size()==0){   //no move case
                    bestMove_helper2(beta, opp, k-1,halt_length+1);
                    if(beta[0]<=alphabeta[0]) pruned=1;    //prune this case since alpha here is still higher than parent beta
                }
                if (pruned==0) alphabeta[0] = beta[0];     //if the beta is better than alpha
            }
        }
        return best_move;
    }

    public int boardScore() {
        /* Complete this function to return num_black_tiles - num_white_tiles if turn = 0, 
         * and num_white_tiles-num_black_tiles otherwise. 
        */
        int blackpieces = 0;int whitepieces = 0;
        //traversing the whole board
        for (int i=0;i<8;i++){
            for (int j =0;j<8;j++){
                if (board[i][j]==0) blackpieces+=1;
                else if (board[i][j]==1) whitepieces+=1;
            }
        }
        if (this.turn==0) return blackpieces-whitepieces;
        else return whitepieces-blackpieces;
    }

    private int bestMove2(int k) {
        /* Complete this function to build a Minimax tree of depth k (current board being at depth 0),
         * for the current player (siginified by the variable turn), and propagate scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j; return i*8+j
         * In case of ties, return the smallest integer value representing the tile with best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the boardScore() is the score
         * for the same player throughout the Minimax tree.
        */
        ArrayList<Integer> alpha = new ArrayList<Integer>(1);alpha.add(Integer.MAX_VALUE); //alpha value for pruning of child nodes
        int[] best_move = bestMove_helper(k, this.turn,alpha);
        return best_move[0];
    }
    public int bestMove(int k){
        int[] beta = {Integer.MAX_VALUE};
        return bestMove_helper2(beta, this.turn, k,0);
    }

    public ArrayList<Integer> fullGame(int k) {
        /* Complete this function to compute and execute the best move for each player starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and the turn
         * at each step. In the end, modify the winner variable as required.
         */
        ArrayList<Integer> game_sequence = new ArrayList<Integer>(); //initialising the required game_sequence Arraylist
        //environment state vars
        int last_player_moved = 1;
        while(true){
            int next_move = bestMove(k);
            int opp = return_opponent(this.turn);   //opponent turn value
            if (next_move!=-1){
                //adding the next_move to game_sequence list
                game_sequence.add(next_move);
                ArrayList<Integer> move = check_cell_for_valid_move(next_move, this.turn);
                //updating the board
                modify_board(move, this.turn);
                
                if (next_move==40) {print_board(this.board);System.out.println(this.turn);System.out.println(move);}
                //updating the turn variable
                this.turn = opp;
                //updating the last_player_moved var
                last_player_moved = 1;
            }
            else{
                if (last_player_moved!=1) break;    //the game finishes
                else{
                    last_player_moved=0;
                    //changing the turn
                    this.turn = opp;
                }
            }
        }
        //updating the winner
        int opp = return_opponent(this.turn);   //opponent at the game end
        int score = boardScore();
        if (score>0) this.winner = this.turn;
        else if(score<0) this.winner = opp;
        return game_sequence;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }

    public static void print_board(int[][] board){
        for (int i=0;i<8;i++){
            for (int j=0;j<8;j++){
                System.out.print(board[i][j]+" ");
            }System.out.println();
        }
    }
}