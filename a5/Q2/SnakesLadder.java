import java.io.*;
import java.util.*;

public class SnakesLadder extends AbstractSnakeLadders {
	
	int N, M;
	int snakes[];
	int ladders[];
	ArrayList<Integer> ladder_sources;	//student created global variable
	int[][] board_BFS;		//student created global variable
	/*board_BFS is 2-d array which stores the dist(ith cell)from 0 as board_BFS[i][0] and dist(ith cell)from this.N as Board_BFS[i][1] */
	public SnakesLadder(String name)throws Exception{
		File file = new File(name);
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.N = Integer.parseInt(br.readLine());
        this.M = Integer.parseInt(br.readLine());

		//declaring and initialising reverse snakes and reverse ladders array
		int[] rev_snakes = new int[this.N+1];int[] rev_ladders = new int[this.N+1];
		//instantiating snakes and ladders array
	    snakes = new int[this.N];
		ladders = new int[this.N];
		//initialising above array elements as -1
	    for (int i = 0; i < this.N; i++){
			snakes[i] = -1;rev_snakes[i]=-1;
			ladders[i] = -1;rev_ladders[i]=-1;
		}
		//instantiating ladder_sources arraylist
		ladder_sources = new ArrayList<Integer>(this.M);
		for(int i=0;i<this.M;i++){
            String e = br.readLine();
            StringTokenizer st = new StringTokenizer(e);
            int source = Integer.parseInt(st.nextToken());
            int destination = Integer.parseInt(st.nextToken());

			if(source<destination){
				ladders[source] = destination;
				rev_ladders[destination] = source;
			}
			else{
				snakes[source] = destination;
				rev_snakes[destination] = source;
			}
        }

		//my code starts from here
		//adding ladders source cells to the ladder_sources arrylist
		for (int i=1;i<ladders.length;i++){
			if (ladders[i]!=-1) ladder_sources.add(i);
		}
		//initialising the board_BFS arraylist
		board_BFS = new int[this.N+1][2];

		//initialising cells_queue for the BFS
		ArrayList<Integer> cells_queue = new ArrayList<Integer>();

		//initialising required environment vars
		int cells_in_current_level = 0;	//number of cells in current level of BFS yet to be explored
		int cells_in_next_level = 0;	//number of cells discovered in current level cells exploration
		int level =0;	//current level of BFS

		//adding cell 0 to the cells_queue
		cells_queue.add(0);cells_in_current_level+=1;
		board_BFS[0][0]=0;		//setting the level=0 in BFS(done from cell'0') of the starting cell '0'

		//doing the BFS
		while(!cells_queue.isEmpty()){
			//assumptions: current level is correct,number of cells in current level(non-zero) and next level is correct, cells in queue are visited and their level is set
			int cell = cells_queue.remove(0);cells_in_current_level-=1;

			for (int i=1;i<=6;i++){	//adding the next move cells(next six cells) from current cell to the queue
				int next_cell = cell+i;
				if (next_cell<=this.N && board_BFS[next_cell][0]==0){	//if next ith cell is unvisited,add it to queue
					board_BFS[next_cell][0]=level+1;	//setting next_cell level to next level
					if (next_cell!=this.N){
						while(ladders[next_cell]!=-1 || snakes[next_cell]!=-1){	
							if (ladders[next_cell]!=-1){//if ladder starts from current cell
								next_cell = ladders[next_cell];
								//setting next_cell level after a ladder to current level (only if next_cell is not visited)
								if(board_BFS[next_cell][0]==0) board_BFS[next_cell][0]=level+1;
							}
							else if(snakes[next_cell]!=-1){	//if snake starts from current cell
								next_cell = snakes[next_cell];
								//setting next_cell level after a snake to current level (only if next_cell is not visited)
								if(board_BFS[next_cell][0]==0) board_BFS[next_cell][0]=level+1;
							}
							if (next_cell==this.N) break;
						}	//note above while loop section assumes that only one of the snakes or ladders starts from current cell
					}

					//adding the next_cell to queue  and incrementing cells_in_next_level
					cells_queue.add(next_cell);cells_in_next_level+=1;	
				}
			}

			//modifying the environment vars for next iteration
			if(cells_in_current_level==0){
				level+=1;
				cells_in_current_level = cells_in_next_level;
				cells_in_next_level = 0;
			}
		}
		
		//doing another BFS starting from cell'this.N'

		//modifying the env vars
		cells_in_current_level = 0;	//number of cells in current level of BFS yet to be explored
		cells_in_next_level = 0;	//number of cells discovered in current level cells exploration
		level =0;	//current level of BFS

		//adding cell this.N to the cells_queue
		cells_queue.add(this.N);cells_in_current_level+=1;
		board_BFS[this.N][1]=0;		//setting the level=0 in BFS(done from cell'this.N') of the starting cell 'this.N' 

		//doing the BFS
		while(!cells_queue.isEmpty()){
			//assumptions: current level is correct,number of cells in current level(non-zero) and next level is correct, cells in queue are visited and their level is set
			int cell = cells_queue.remove(0);cells_in_current_level-=1;

			for (int i=1;i<=6;i++){	//adding the next move cells(prev six cells) from current cell to the queue
				int next_cell = cell-i;
				if (next_cell>=1 && board_BFS[next_cell][1]==0){	//if next ith cell is unvisited,add it to queue
					board_BFS[next_cell][1]=level+1;	//setting next_cell level to next level
					while(rev_ladders[next_cell]!=-1 || rev_snakes[next_cell]!=-1){	
						if (rev_ladders[next_cell]!=-1){//if rev_ladder starts from current cell
							next_cell = rev_ladders[next_cell];
							//setting next_cell level after a rev_ladder to current level (only if next_cell is not visited)
							board_BFS[next_cell][1]=level+1;
						}
						else if(rev_snakes[next_cell]!=-1){	//if rev_snake starts from current cell
							next_cell = rev_snakes[next_cell];
							//setting next_cell level after a rev_snake to current level (only if next_cell is not visited)
							board_BFS[next_cell][1]=level+1;
						}
					}	//note above while loop section assumes that only one of the rev_snakes or rev_ladders starts from current cell

					//adding the next_cell to queue and incrementing cells_in_next_level
					cells_queue.add(next_cell);cells_in_next_level+=1;	
				}

			}
			//modifying the environment vars for next iteration
			if(cells_in_current_level==0){
				level+=1;
				cells_in_current_level = cells_in_next_level;
				cells_in_next_level = 0;
			}
		}
	}
    
	public int OptimalMoves()
	{
		/* Complete this function and return the minimum number of moves required to win the game. */
		return board_BFS[this.N][0];
	}

	public int Query(int x, int y)
	{
		/* Complete this function and 
			return +1 if adding a snake/ladder from x to y improves the optimal solution, 
			else return -1. */
		if (board_BFS[x][0]!=0 && (board_BFS[y][1]!=0 || y==N)){
			int dist_0toX = board_BFS[x][0];
			int dist_YtoN = board_BFS[y][1];
			int dist_0toN = board_BFS[this.N][0];
			if (dist_0toN==0)  return 1;
			else{
				if (dist_0toX+dist_YtoN<dist_0toN)  return 1;
			}
		}
		return -1;
	}

	public int[] FindBestNewSnake()
	{
		int result[] = {-1, -1};
		/* Complete this function and 
			return (x, y) i.e the position of snake if adding it increases the optimal solution by largest value,
			if no such snake exists, return (-1, -1) */
		int new_optimal_moves = Integer.MAX_VALUE;
		for (int i=0;i<ladder_sources.size()-1;i++){
			int source = ladder_sources.get(i);
			if (board_BFS[source][0]!=0){//note:no ladder or snakes starts at 0 or this.N, so no problem
				for (int j = i+1;j<ladder_sources.size();j++){
					if(ladder_sources.get(j)<ladders[source]){
						/*ladder_source.get(i)---> source of first ladder  (source)
						* ladder_sources.get(j)---> source of second ladder
						* ladders[ladder_sources.get(i)]/ladders[source]--->dest of first ladder
						* ladders[ladder_sources.get(j)]--> dest of second ladder  (dest)
						*/
						int dest = ladders[ladder_sources.get(j)];
						if (dest>ladders[source] && (board_BFS[dest][1]!=0 || dest==this.N)){//note:no ladder or snakes starts at 0 or this.N, so no problem
							int new_optimal_moves2 = board_BFS[source][0]+board_BFS[dest][1];
							int old_optimal_moves = board_BFS[this.N][0];
							if (new_optimal_moves2<old_optimal_moves && new_optimal_moves2<new_optimal_moves){
								result[0]=ladders[source];result[1]=ladder_sources.get(j);
								new_optimal_moves = new_optimal_moves2;
							}
						}
					}else break;
				}
			}
		}
		return result;
	}

}