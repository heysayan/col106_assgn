import heap_package.Node;
import heap_package.Heap;
import java.util.ArrayList;

public class Poker{

	private int city_size;            // City Population
	public int[] money;		         // Denotes the money of each citizen. Citizen ids are 0,1,...,city_size-1.
	private Heap max_lim_heap;		//Denotes the min heap(implemented by negating elements of maxheap) that stores the amount to be earned to reach upper limit of playing
	private Heap min_lim_heap;		//Denotes the min heap that stores the amount to be lost to reach lower limit of playing
	private Heap profit_Heap;		//Denotes the max heap storing total profit of citizens
	private Heap loss_Heap;			//Denotes the max heap storing the total loss of citizens

	/* 
	   1. Can use helper methods but they have to be kept private. 
	   2. Allowed to use only PriorityQueue data structure globally but can use ArrayList inside methods. 
	   3. Can create at max 4 priority queues.
	*/

	public void initMoney(){
		// Do not change this function.
		for(int i = 0;i<city_size;i++){
			money[i] = 100000;							// Initially all citizens have $100000. 
		}
	}

	public Poker(int city_size, int[] players, int[] max_loss, int[] max_profit){

		/* 
		   1. city_size is population of the city.
		   1. players denotes id of the citizens who have come to the Poker arena to play Poker.
		   2. max_loss[i] denotes the maximum loss player "players[i]"" can bear.
		   3. max_profit[i] denotes the maximum profit player "players[i]"" will want to get.
		   4. Initialize the heap data structure(if required). 
		   n = players.length 
		   Expected Time Complexity : O(n).
		*/
		this.city_size = city_size;
		this.money = new int[this.city_size];
		this.initMoney();

		// To be filled in by the student
		//preparing profit_heap and loss_heap
		int[] zero_arr=new int[city_size];	// a array with zeroes and size same as players array
		for (int i=0;i<players.length;i++) {zero_arr[i]=0;}
		try{
			profit_Heap = new Heap(city_size,players,zero_arr);
			loss_Heap = new Heap(city_size,players,zero_arr);
		}catch(Exception en){en.printStackTrace();}

		//preparing max_lim_heap and min_lim_heap
		int [] negated_maxprofit = new int[players.length];int[] negated_maxloss = new int[players.length];
		for (int j=0;j<players.length;j++){negated_maxloss[j]=(-1)*max_loss[j];negated_maxprofit[j]=(-1)*max_profit[j];}
		try{
			max_lim_heap = new Heap(city_size,players,negated_maxprofit);
			min_lim_heap = new Heap(city_size,players,negated_maxloss);
		}catch(Exception en){en.printStackTrace();}
	}

	public ArrayList<Integer> Play(int[] players, int[] bids, int winnerIdx){

		/* 
		   1. players.length == bids.length
		   2. bids[i] denotes the bid made by player "players[i]" in this game.
		   3. Update the money of the players who has played in this game in array "money".
		   4. Returns players who will leave the poker arena after this game. (In case no
		      player leaves, return an empty ArrayList).
                   5. winnerIdx is index of player who has won the game. So, player "players[winnnerIdx]" has won the game.
		   m = players.length
		   Expected Time Complexity : O(mlog(n))
		*/
		int winner = players[winnerIdx];					// Winner of the game.

		ArrayList<Integer> playersToBeRemoved = new ArrayList<Integer>();     // Players who will be removed after this game. 
		// To be filled in by the student
		int profit_of_winner = 0;
		for (int i=0;i<players.length;i++){
			if (i==winnerIdx) continue;
			profit_of_winner+=bids[i];	//winner will receive this bid amount for winning
			money[players[i]]-=bids[i];	//reducing amount of lost bid from total money
			//updating value of player in min_lim_heap
			try{min_lim_heap.update(players[i],bids[i]);}catch(Exception en){en.printStackTrace();}
			//updating value of player in max_lim_heap
			try{max_lim_heap.update(players[i], (-1)*bids[i]);}catch(Exception en){en.printStackTrace();}
			//updating total profit and loss heap of players
			try{profit_Heap.update(players[i],(-1)*bids[i]);loss_Heap.update(players[i],bids[i]);}catch(Exception en){en.printStackTrace();}
		}
		{//doing required updates for winner
			money[winner]+=profit_of_winner;
			//updating value of player in min_lim_heap
			try{min_lim_heap.update(winner,(-1)*profit_of_winner);}catch(Exception en){en.printStackTrace();}
			//updating value of player in max_lim_heap
			try{max_lim_heap.update(winner, profit_of_winner);}catch(Exception en){en.printStackTrace();}
			//updating total profit and loss heap of players
			try{profit_Heap.update(winner,profit_of_winner);loss_Heap.update(winner,(-1)*profit_of_winner);}catch(Exception en){en.printStackTrace();}
			
		}
		ArrayList<Integer> min_lim_citizens;ArrayList<Integer> max_lim_citizens; //the citizens to be removed
		try{	//removing the players who'll leave from heap and adding into playerstoberemoved arraylist
			
			while(min_lim_heap.getMaxValue()>=0) {
				min_lim_citizens = min_lim_heap.deleteMax();
				for (int i=0;i<min_lim_citizens.size();i++){//removing deleted min_lim_citizens from max_lim_heap, if min_lim_citizens is not init then it would be handled
					int a=min_lim_citizens.get(i);
					max_lim_heap.update(a,100000000);max_lim_heap.deleteMax();
				}
				playersToBeRemoved.addAll(min_lim_citizens);
			}
			while (max_lim_heap.getMaxValue()>=0) {
				max_lim_citizens=max_lim_heap.deleteMax();
				for (int i=0;i<max_lim_citizens.size();i++){
					int a=max_lim_citizens.get(i);
					min_lim_heap.update(a,100000000);min_lim_heap.deleteMax();
				}
				playersToBeRemoved.addAll(max_lim_citizens);	
			}
		}catch(Exception en){en.printStackTrace();}
		return playersToBeRemoved;
	}

	public void Enter(int player, int max_loss, int max_profit){

		/*
			1. Player with id "player" enter the poker arena.
			2. max_loss is maximum loss the player can bear.
			3. max_profit is maximum profit player want to get. 
			Expected Time Complexity : O(logn)
		*/

		// To be filled in by the student
		//inserting the player into the min_lim_heap and max_lim_heap
		try{
			min_lim_heap.insert(player,(-1)*(max_loss));max_lim_heap.insert(player,(-1)*max_profit);
			profit_Heap.insert(player,money[player]-100000);loss_Heap.insert(player,100000-money[player]);
		}catch(Exception en){en.printStackTrace();}
	}

	public ArrayList<Integer> nextPlayersToGetOut(){

		/* 
		   Returns the id of citizens who are likely to get out of poker arena in the next game. 
		   Expected Time Complexity : O(1). 
		*/
		ArrayList<Integer> players = new ArrayList<Integer>();    // Players who are likely to get out in next game.

		// To be filled in by the student
		try{
			int min_diff_from_max_lim = (-1)*max_lim_heap.getMaxValue();
			int min_diff_from_min_lim = (-1)*min_lim_heap.getMaxValue();
			if (min_diff_from_max_lim<min_diff_from_min_lim) players.addAll(max_lim_heap.getMax());
			else if (min_diff_from_max_lim>min_diff_from_min_lim)players.addAll(min_lim_heap.getMax());
			else {ArrayList<Integer> max_lim_max = max_lim_heap.getMax();ArrayList<Integer> min_lim_max = min_lim_heap.getMax();
				players.addAll(max_lim_max);players.addAll(min_lim_max);
				max_lim_max.retainAll(min_lim_max);
				for(int i=0;i<max_lim_max.size();i++){	//removing redundant occurence
					players.remove(max_lim_max.get(i));
				}
			}
		}catch(Exception en){en.printStackTrace();}
		return players;
	}

	public ArrayList<Integer> playersInArena(){

		/* 
		   Returns id of citizens who are currently in the poker arena. 
		   Expected Time Complexity : O(n).
		*/
		ArrayList<Integer> currentPlayers = new ArrayList<Integer>();    // citizens in the arena.

		// To be filled in by the student
		try{currentPlayers.addAll(min_lim_heap.getKeys());}catch(Exception en){en.printStackTrace();}
		return currentPlayers;
	}

	public ArrayList<Integer> maximumProfitablePlayers(){

		/* 
		   Returns id of citizens who has got most profit. 
			
		   Expected Time Complexity : O(1).
		*/
		ArrayList<Integer> citizens = new ArrayList<Integer>();    // citizens with maximum profit.

		// To be filled in by the student
		try{citizens.addAll(profit_Heap.getMax());}catch(Exception en){en.printStackTrace();}
		return citizens;
	}

	public ArrayList<Integer> maximumLossPlayers(){

		/* 
		   Returns id of citizens who has suffered maximum loss. 
			
		   Expected Time Complexity : O(1).
		*/
		ArrayList<Integer> citizens = new ArrayList<Integer>();     // citizens with maximum loss.

		// To be filled in by the student
		try{citizens.addAll(loss_Heap.getMax());}catch(Exception en){en.printStackTrace();}
		return citizens;
	}
}