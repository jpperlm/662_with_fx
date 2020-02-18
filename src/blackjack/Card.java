package blackjack;

import java.util.ArrayList;

// Represents a playing card
// The card is created to accept values typically used for a deck of 52 playing cards but
//  is left abstract enough that it could be used for other games as the code expands
//  (potentially more games than just blackjack that use non standard cards)
public class Card {
    Integer value; // The value of the card, typically 1-13
    String short_value; // The abbreviation for the value, typically 1=>A, 2=>2, ... 13=>K
    String long_value; // The full name for the value, 1=>Ace, 2=>Two, 13=>King
    String short_suit; // The short suit H, S, C, D
    String long_suit; // The long suit, Hearts, Spades, Clubs, Diamonds
    ArrayList<Integer> possibleValues;

    // Constructor for when a card only has a single value (Accepts an Integer as the final param)
    public Card(Integer v, String sv, String lv, String ss, String ls, Integer pv){
        this.value = v;
        this.short_value = sv;
        this.long_value = lv;
        this.short_suit = ss;
        this.long_suit = ls;
        ArrayList<Integer> vals = new ArrayList<>();
        vals.add(pv);
        this.possibleValues = vals;
    }

    // Constructor for when a card has multiple values (Accepts an ArrayList as the final param)
    public Card(Integer v, String sv, String lv, String ss, String ls, ArrayList<Integer> possibleValues){
        this.value = v;
        this.short_value = sv;
        this.long_value = lv;
        this.short_suit = ss;
        this.long_suit = ls;
        this.possibleValues = possibleValues;
    }

    // Getters
    public String fullSuit() {
        return this.long_suit;
    }
    public String faceValue() {
        return this.short_value;
    }
    public Boolean hasMultipleValues() {
        return this.possibleValues.size() > 1;
    }
    public Integer getSingleValue() {
        return this.possibleValues.get(0);
    }
    public ArrayList<Integer> getPossibleValues() {
        return this.possibleValues;
    }

    // Gets the 'Count' of the card in reference to card counting
    // 2-6 = 1
    // 10-K & A = -1
    // Others = 0
    public Integer getCountValue() {
        if (this.value >= 2 && this.value <= 6 ) {
            return 1;
        } else if (this.value >= 10 || this.value.equals(1)) {
            return -1;
        }
        return 0;
    }

    // Intent: Return a string representation of the possibleValues array
    // Example: possibleValues = [10,20]. Returns "10, 20"
    // Precondition: possibleValues is a non null array
    // Post condition: returns a string
    public String possibleValuesString() {
        String s = "";
        if (this.possibleValues.size() > 1) {
            s = this.possibleValues.get(0) + ", " + this.possibleValues.get(1);
        } else {
           s = "" + this.possibleValues.get(0);
        }
        return s;
    }

    // Alternative toString method
    public String longToString() {
        return this.long_value + " ( " + this.possibleValuesString() + " ) of " + this.fullSuit();
    }
    // Overrides the toString method
    @Override
    public String toString() {
        return this.faceValue() + " " + this.fullSuit();
    }

}
