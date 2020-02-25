package FXRunner;

import blackjack.BlackJackTable;
import blackjack.Player;
import blackjack.RandomPlayer;
import blackjack.UserPlayer;

import java.util.ArrayList;
import java.util.HashMap;

public class BlackJackRunner {
    public static void main(String[] args){
        run_table(args);
    }

    private static void run_table(String[] args) {

        Connect app = new Connect("blackjack");
//        app.delete_table();
        app.create_table();
        app.insert_default_players();
        // Creates the User Player. Sets name to input or defaults to default value.
        String name = args.length > 0 ? args[0] : "User (you)";
        app.create_player_if_not_exist(name, "User");

        ArrayList<Player> players = new ArrayList<>();
        app.select_all_players().stream().forEach(player -> {
            if (player.get("type").toString() == "Random") {
                players.add(new RandomPlayer(player.get("name").toString(), 100.0));
            } else {
                players.add(new UserPlayer(player.get("name").toString(), 100.0));
            }
        });

        // Creates a new blackjacktable
        BlackJackTable table_1 = new BlackJackTable(1, 3, 3);

        // Adds the Players to the table
        table_1.loop(players);
    }

}