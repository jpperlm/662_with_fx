package blackjack;

import java.util.ArrayList;

// Represents a Generic Player
public class Player {
    String name;
    Double money;
    History<Player, String> player_history = new History<>();

    public Player(String name, Double money){
        this.name = name;
        this.money = money;
    }
    public String getName() {
        return this.name;
    }
    public History<Player, String> getHistoryWrapper() { return this.player_history; }

    // To be overwritten by subclasses
    public Integer getDecision(ArrayList<String> options) { return 0; }

    @Override
    public String toString() {
        return this.name;
    }

}
