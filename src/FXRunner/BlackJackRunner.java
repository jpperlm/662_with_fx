package FXRunner;

import blackjack.BlackJackTable;
import blackjack.Player;
import blackjack.RandomPlayer;
import blackjack.UserPlayer;

import java.util.ArrayList;

public class BlackJackRunner {
    public static void main(String[] args){
        run_table(args);
    }

    private static void run_table(String[] args) {
        // Creates two different Random Player Objects
        Player random_player_1 = new RandomPlayer("Random P1", 100.0);
        Player random_player_2 = new RandomPlayer("Random P2", 100.0);

        // Creates the User Player. Sets name to input or defaults to default value.
        String name = args.length > 0 ? args[0] : "User (you)";
        Player user_player = new UserPlayer(name, 100.0);

        ArrayList<Player> players = new ArrayList<>();
        players.add(random_player_1);
        players.add(random_player_2);
        players.add(user_player);

        // Creates a new blackjacktable
        BlackJackTable table_1 = new BlackJackTable(1, 3, 3);

        // Adds the Players to the table
        table_1.loop(players);
    }

}