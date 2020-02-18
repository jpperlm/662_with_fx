package blackjack;

import java.util.ArrayList;

// Represents a Seat (at a table)
// Seats have cards that can be given to them
// Seats can be occupied or empty
public class Seat {
    Player player;
    Boolean occupied = false;
    Boolean dealer = false;
    ArrayList<Card> cards = new ArrayList<Card>();

    public Seat() {}

    // Adds a player to the seat
    // Sets the occupied flag to true
    public void addPlayer(Player p) {
        this.player = p;
        this.occupied = true;
    }

    // Removes a player from the seat
    public void removePlayer() {
        this.player = null;
        this.occupied = false;
    }

    // Clears the cards array, equivalent of clearing the seat
    public void clearCards() {
        this.cards.clear();
    }

    public void addCard(Card c) {
        this.cards.add(c);
    }

    // INTENT: Gets the card from the specified index, returns null if no cards.
    // PRECONDITION 1: An integer i is passed into the method
    // PRECONDITION 2: The cards array contains an array of cards
    public Card getCard(Integer i) {
        if (this.cards.size() > i) {
            return this.cards.get(i);
        }
        return null;
    }

    // INTENT: Sets the dealer flag to true
    public void makeDealer() {
        this.dealer = true;
    }

    // INTENT: IF there is a player in the seat, then we return the players name, otherwise return Empty
    // EXAMPLE: this.player = a player with the name "John Doe". We will return 'John Doe';
    public String getPlayer() {
        if (this.occupied) {
            return this.player.getName();
        } else {
            return "Empty";
        }
    }

    //INTENT: Gets the current TOTAL value of all of the cards in the current seat.
    //Example: If the seat has 2 cards, an Ace and a Jack then the value will be [11, 21]
    //PRECONDITION 1: this.cards is an array of cards with at least one card
    //PRECONDITION 2: Each card has at least one value associated with it
    //POST CONDITION: The return value will be a non null array of integers
    public ArrayList<Integer> getValue() {
        ArrayList<Integer> possibleValues = new ArrayList<>();
        possibleValues.add(0);
        for (Card c: this.cards) {
            ArrayList<Integer> newValues = new ArrayList<>();
            if (c.hasMultipleValues()) {
                for (Integer v: c.getPossibleValues()) {
                    for (Integer i: possibleValues) {
                        newValues.add(i+v);
                    }
                }
            } else {
                for (Integer i: possibleValues) {
                    newValues.add(i + c.getSingleValue());
                }
            }
            possibleValues = newValues;
        }
        return possibleValues;
    }

    // Returns the player stored in this.player
    public Player getPlayerObject() {
        return this.player;
    }

}
