package blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Represents a Deck (of cards)
// A deck typically has 52 cards but this is customizable based
//  from the two files cards.csv and suits.csv.
// Potentially break this into a parent or abstract class and then
//  have a `RegularDeck` child class that is made up of 52 cards
public class Deck {
    ArrayList<Card> cards = new ArrayList<Card>();

    public Deck(){
        this.reset();
    }

    // Clears the cards from the Deck and re-initializes them from the specified files
    private void reset(){
        this.cards.clear();
        try {
            Scanner scanner_suits = new Scanner(new File("/Users/jasonperlman/IdeaProjects/Perlman_BlackJack_Tutor/src/blackjack/suits.csv"));
            Scanner scanner_cards = new Scanner(new File("/Users/jasonperlman/IdeaProjects/Perlman_BlackJack_Tutor/src/blackjack/cards.csv"));
            ArrayList<String[]> suits = new ArrayList<>();
            ArrayList<String[]> cards = new ArrayList<>();
            while (scanner_suits.hasNextLine()) {
                suits.add(scanner_suits.nextLine().split(","));
            }
            scanner_suits.close();
            while (scanner_cards.hasNextLine()) {
                cards.add(scanner_cards.nextLine().split(","));
            }
            scanner_cards.close();
            for (String[] suit_items: suits){
                String suit_short = suit_items[1];
                String suit_long = suit_items[2];
                for (String[] items: cards) {
                    ArrayList<Integer> act = new ArrayList<>();
                    for (String val: items[3].split("/") ){
                        act.add(Integer.parseInt(val));
                    }
                    this.cards.add(new Card(Integer.parseInt(items[0]), items[1], items[2], suit_short, suit_long, act));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Getters
    public ArrayList<Card> cards() {
        return this.cards;
    }

}
