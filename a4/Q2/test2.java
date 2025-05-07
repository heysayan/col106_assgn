public class test2 {
    public static void main(String[] args) {
        int[] players = new int[]{ 4, 2, 5, 0, 1 };
        int[] max_loss = new int[]{ 2567, 70508, 96725, 74281, 75762 };
        int[] max_profit = new int[]{ 22401, 21216, 21787, 14022, 47336 };
        Poker p = new Poker(8, players, max_loss, max_profit);
        System.out.println("money[3]: "+p.money[3]+" money[7]: "+p.money[7]+" money[5]: "+p.money[5]);
        int[] pokerPlayers = new int[]{ 0, 2, 4, 1 };
        int[] bids = new int[]{37824, 85093, 21223, 86856};
        int winnerIdx = 2;
        System.out.println("First play: "+p.Play(pokerPlayers, bids, winnerIdx));
/*
        int[] players = new int[]{ 4, 72, 12, 17, 1 };
        int[] max_loss = new int[]{ 4100, 2312, 175, 9007, 63125 };
        int[] max_profit = new int[]{ 7500, 6010, 1700, 2100, 31275 };
        Poker p = new Poker(100, players, max_loss, max_profit);
        int[] pokerPlayers = new int[]{ 4, 17, 12 };
        int[] bids = new int[]{2000, 1000, 500};
        int winnerIdx = 1;
        System.out.println("First play: "+p.Play(pokerPlayers, bids, winnerIdx));
        System.out.println("money[4]: "+p.money[4]+" money[17]: "+p.money[17]+" money[12]: "+p.money[12]);
        System.out.println(p.playersInArena());
        p.Enter(3, 1500, 1500);
        System.out.println(p.playersInArena());
        System.out.println(p.nextPlayersToGetOut());
        p.Enter(12, 250, 800);
        System.out.println("money[12]: "+p.money[12]);
        int[] pokerPlayers2 = new int[]{ 12, 3, 1, 4 };
        int[] bids2 = new int[]{200, 100, 100, 200};
        int winnerIdx2 = 0;
        System.out.println(p.Play(pokerPlayers2, bids2, winnerIdx2));
        System.out.println("money[4]: "+p.money[4]+" money[1]:"+p.money[1]+" money[3]: "+p.money[3]+"  money[12]: "+p. money[12]);
        System.out.println(p.nextPlayersToGetOut());
        System.out.println(p.playersInArena());
        System.out.println(p.maximumProfitablePlayers());
        System.out.println(p.maximumLossPlayers());
        */
    }
}
