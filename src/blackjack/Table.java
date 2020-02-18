package blackjack;

import java.util.ArrayList;

// INTENT: Represents a generic table.
//  Contains the base functionality that any table should have
//  Includes an array of Seats as well as flags that indicate the max and min seats allowed
//      before a game can be started at the table
public abstract class Table {
    Integer maxSeats;
    Integer minSeats;
    ArrayList<Seat> seats = new ArrayList<Seat>();
    public abstract void loop(ArrayList<Player> potential_players);

}
