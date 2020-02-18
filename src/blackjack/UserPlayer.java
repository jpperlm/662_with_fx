package blackjack;

import java.util.ArrayList;

// Represents a User who will be able to give input
public class UserPlayer extends Player implements Visualize {

    public UserPlayer(String name, Double money) {
        super(name, money);
        this.player_history.add(this);
    }

    // INTENT: Get a decision from a player in the form of an Integer Response
    // EXAMPLE: options = ["Left", "Right"], returns 0 (indicates left was selected)
    // PRECONDITION 1: An array of options of at least length 1 is passed into the method
    // POST CONDITION: the return value shall be between 0 and the length of the array -1
    @Override
    public Integer getDecision(ArrayList<String> options) {
        return this.waitResponse(options);
    }

}
