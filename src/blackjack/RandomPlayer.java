package blackjack;

import java.util.ArrayList;
import java.util.Random;

// A player who will always randomize their decision
public class RandomPlayer extends Player {

    public RandomPlayer(String name, Double money) {
        super(name, money);
        this.player_history.add(this);
    }

    // Takes an array of choices and returns a random integer from 0 to 1 - size of array
    public Integer getDecision(ArrayList<String> options) {
       return new Random().nextInt(options.size());
    }

}
